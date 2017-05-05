package com.dataagg.commons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dataagg.commons.domain.EFile;
import com.dataagg.util.Constans;
import com.dataagg.commons.dao.FileDao;
import com.dataagg.commons.service.FileService;

/**
 * 附件管理service实现类
 * Created by carlos on 2017/3/29.
 */
@Service
public class FileService{
	@Autowired
	private FileDao fileDao;
	
	public EFile insert(EFile file){
		file.setDelFlag(Constans.POS_NEG.NEG);
		return fileDao._insert(file);
	}
	public EFile update(EFile file){
		file.setDelFlag(Constans.POS_NEG.NEG);
		return fileDao._update(file) > 0 ? file : null;
	}
	public boolean delete(long id) {
		EFile file = fileDao.fetch(id);
		if(file ==null){
			return false;
		}
//		file.setDelFlag(Constans.POS_NEG.POS);
		return fileDao.delete(file.getId())>0 ? true : false;
	}
	
	/**
	 * 通用的新增和修改方法
	 * @param file
	 * @return
	 */
	public EFile save(EFile file){
		if (file.getId() != null && file.getId()>0) {
			return update(file);
		}else{
			return insert(file);
		}
	}
	
}
