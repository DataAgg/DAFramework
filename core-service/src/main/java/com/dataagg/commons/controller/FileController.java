package com.dataagg.commons.controller;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dataagg.commons.domain.EFile;
import com.dataagg.commons.vo.ActionResultObj;
import com.dataagg.util.Constans;
import com.dataagg.util.FileUtil;
import com.dataagg.util.IdGen;
import com.dataagg.util.SearchQueryJS;
import com.dataagg.util.WMap;
import com.dataagg.commons.dao.FileDao;
import com.dataagg.commons.service.FileService;

import jodd.util.StringUtil;


/**
 * Created by carlos on 2017/3/29.
 * 附件管理 controller
 */
@RestController
@RequestMapping("/file")
public class FileController {
	private Logger LOG = LoggerFactory.getLogger(FileController.class);
	@Autowired
	public FileService fileService;
	@Autowired
	public FileDao fileDao;
	
	@Value("${file.rootPath}")
	public String rootPath;
	@Value("${file.filePath}")
	public String filePath;
	
	@RequestMapping(value="/list")
	public ActionResultObj list(@RequestBody SearchQueryJS queryJs){
		ActionResultObj result = new ActionResultObj();
		try{
			Pager pager = queryJs.toPager();
			WMap query = queryJs.getQuery();
			
			//处理查询条件
			Criteria cnd = Cnd.cri();
			if(query.get("type") != null && StringUtil.isNotBlank(query.get("type").toString())){
				cnd.where().andEquals("type", query.get("type").toString());
			}
			cnd.getGroupBy().groupBy("type");
			cnd.getOrderBy().desc("sort");
			//分页查询
			List<EFile> projectList = fileDao.query(cnd, pager);
			
			//处理返回值
			WMap map = new WMap();
			if(queryJs.getQuery() != null){
				map.putAll(queryJs.getQuery());
			}
			map.put("data", projectList);
			map.put("page", queryJs.toWPage(pager));
			result.ok(map);
			result.okMsg("查询成功！");
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("查询失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	@RequestMapping(value="/grouping/{grouping}")
	public ActionResultObj grouping(@PathVariable String grouping){
		ActionResultObj result = new ActionResultObj();
		try{
			if(StringUtil.isNotBlank(grouping)){
					List<EFile> fileList = fileDao.query(Cnd.where("grouping", "=", grouping).and("del_flag", "=", Constans.POS_NEG.NEG).orderBy("id", "desc"));
					//处理返回值
					WMap map = null;
					List<WMap> mapList = new ArrayList<>();
					for (EFile eFile : fileList) {
						map = new WMap();
						map.put("id",eFile.getId());
						map.put("name",eFile.getName());
						mapList.add(map);
					}
					map = new WMap();
					map.put("data",mapList);
					result.setData(map);
					result.okMsg("查询成功！");
			}else{
				result.errorMsg("查询失败，附件不存在！");
			}
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("查询失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	@RequestMapping(value="/get/{id}")
	public ActionResultObj get(@PathVariable Long id){
		ActionResultObj result = new ActionResultObj();
		try{
			if(id != 0){
				EFile file = fileDao.fetch(id);
				if(file != null){
					result.ok(file);
					result.okMsg("查询成功！");
				}else{
					result.errorMsg("查询失败！");
				}
			}else{
				result.errorMsg("查询失败，链接不存在！");
			}
		}catch(Exception e){
			LOG.error("查询失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	
	@RequestMapping(value="/delete/{id}")
	public ActionResultObj delete(@PathVariable Long id){
		ActionResultObj result = new ActionResultObj();
		try{
			if(id != 0){
				EFile efile = fileDao.fetch(id);
				if(efile != null){
					if(fileService.delete(id)){
						result.okMsg("删除成功！");
						File file = new File(rootPath+efile.getPath());
						file.delete();
					}else{
						result.errorMsg("删除失败！");
					}
				}else{
					result.errorMsg("删除失败！");
				}
			}else{
				result.errorMsg("删除失败，链接不存在！");
			}
		}catch(Exception e){
			LOG.error("删除失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	
	@RequestMapping(value="/upload")
	public ActionResultObj upload(HttpServletRequest request, HttpServletResponse response){
		ActionResultObj result = new ActionResultObj();
		try{
			String grouping = request.getParameter("grouping");
			if(StringUtil.isBlank(grouping)||grouping.equalsIgnoreCase("null")){
				 grouping = IdGen.uuid();
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			String newFilePath = filePath+"/"+grouping+"/";
			File file = new File(rootPath+ newFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName = null;
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				fileName = mf.getOriginalFilename();
				String newFileName = FileUtil.generateFileName(fileName);// 构成新文件名。

				File uploadFile = new File(rootPath + newFilePath + newFileName);
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				
				EFile eFile = new EFile();
				eFile.setName(FileUtil.getFileNameNotSuffix(fileName)+FileUtil.getFileNameSuffix(fileName));
				eFile.setPath(newFilePath+newFileName);
				eFile.setGrouping(grouping);
				
				if(fileService.save(eFile) != null){
					WMap map = new WMap();
					map.put("data", eFile);
					result.ok(map);
					result.okMsg("上传成功！");
				}else{
					result.errorMsg("上传失败！");
				}
			}
		}catch(Exception e){
			LOG.error("上传失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	
	@RequestMapping(value = "/download/{id}")
	public void downloadFile(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
        	if(id != 0){
        		EFile fileInfo = fileDao.fetch(id);
        		if(fileInfo!= null){
        			String path = fileInfo.getPath();
//        			String suffix = path.split("\\.")[1];
					File file = new File(rootPath+path);
//                    String filename = fileInfo.getName()+"."+suffix;
                    InputStream fis = new BufferedInputStream(new FileInputStream(file));
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    fis.close();
                    response.reset();
                    response.addHeader("Content-Disposition","attachment;filename="
                            + new String(java.net.URLEncoder.encode(fileInfo.getName(), "UTF-8")));
                    response.addHeader("Content-Length","" + file.length());
                    response.setContentType("application/octet-stream");
                    OutputStream toClient = new BufferedOutputStream(
                            response.getOutputStream());
                    toClient.write(buffer);
                    toClient.flush();
                    toClient.close();
        		}
        	}
        }catch(Exception e){
			LOG.error("下载失败，原因："+e.getMessage());
		}
	}
}
