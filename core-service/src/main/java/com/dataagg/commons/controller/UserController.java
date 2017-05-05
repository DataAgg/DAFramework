package com.dataagg.commons.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dataagg.commons.dao.OrgDao;
import com.dataagg.commons.domain.EAccount;
import com.dataagg.commons.domain.EMenu;
import com.dataagg.commons.domain.EOpenUser;
import com.dataagg.commons.domain.EOrg;
import com.dataagg.commons.domain.ERole;
import com.dataagg.commons.domain.EUser;
import com.dataagg.commons.vo.ActionResult;
import com.dataagg.commons.vo.ActionResultList;
import com.dataagg.commons.vo.ActionResultObj;
import com.dataagg.commons.vo.VLogin;
import com.dataagg.commons.dao.AccountDao;
import com.dataagg.commons.dao.MenuDao;
import com.dataagg.commons.dao.RoleDao;
import com.dataagg.commons.dao.UserDao;
import com.dataagg.commons.service.SysUserDetailsService;
import com.dataagg.util.Constans;
import com.dataagg.util.SearchQueryJS;
import com.dataagg.util.SecurityHelper;
import com.dataagg.util.WMap;
import com.dataagg.util.oauth.OAuth2Util;

import jodd.util.StringUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private SysUserDetailsService userDetailsService;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private OrgDao orgDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public Principal getUser(Principal principal) {
		return principal;
	}
	@RequestMapping(value = "/currInfo", method = RequestMethod.GET)
	public ActionResultObj getUser() {
		ActionResultObj result = new ActionResultObj();
		// 获取当前用户
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if(StringUtil.isNotBlank(userDetails.getUsername())){
			EUser currentUser = userDao.fetchByName(userDetails.getUsername());
			EAccount account = accountDao.fetch(currentUser);
			EOrg org = orgDao.fetch(currentUser.getOrgId());
			WMap map = new WMap();
			map.put("userName", account != null ? account.getFullName() : currentUser.getUsername());
			map.put("accountId", account!=null? account.getId():"");
			map.put("orgName", org.getName());
			result.ok(map);
		}else{
			result.errorMsg("获取失败");
		}
		return result;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ActionResult<EAccount> userIndex(Principal principal) {
		ActionResult<EAccount> result = new ActionResult<>();
		EUser user = null;
		if (principal != null && principal instanceof OAuth2Authentication) {
			EOpenUser openUser = OAuth2Util.fetch((OAuth2Authentication) principal);
			user = userDetailsService.checkAndCreate(openUser);
			SecurityHelper.auth(authenticationManager, user.getUsername(), user.getPassword());
		} else if (principal != null && principal.getName() != null) {
			user = userDetailsService.fetchByName(principal.getName());
		}
		EAccount account = accountDao.fetch(user);
		if (account == null) {
			result.msg(-10, "请先完善用户信息!");
		} else {
			result.ok(account);
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void createUser(@Valid @RequestBody EUser user) {
		userDetailsService.create(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ActionResult<Object> loginJs() {
		ActionResult<Object> result = new ActionResult<>();
		result.msg(-10, "请先登陆!");
		return result;
	}

	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public ActionResult<Object> login2(@RequestBody VLogin params) {
		ActionResult<Object> result = new ActionResult<>();
		result.msg(-10, params.getUsername() + "---" + params.getPassword());
		return result;
	}

	@RequestMapping(value = "/loginError", method = RequestMethod.GET)
	public ActionResult<Object> loginError() {
		ActionResult<Object> result = new ActionResult<>();
		result.errorMsg("用户名密码错误!");
		return result;
	}

	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
	public ActionResult<Object> loginSuccess(Principal principal) {
		ActionResult<Object> result = new ActionResult<>();
		result.okMsg("登陆成功");
		result.setData(principal.getName());
		LOG.info(principal.getName() + "登陆成功!");
		return result;
	}

	@RequestMapping(value = "/allMenus", method = RequestMethod.GET)
	public ActionResultList<EMenu> allMenus(Principal principal) {
		ActionResultList<EMenu> result = new ActionResultList<>();
		try {
			 LOG.debug("principal====" + (principal != null ?
			 principal.toString() : "null"));
			Set<String> userAuthorities = userDetailsService.principalAuthorities(principal);
			List<EMenu> all = menuDao.allMenus(userAuthorities).getItems();
//			System.out.println("all size:"+all.size());
//			if(!all.isEmpty()){
//			for (EMenu eMenu : all) {
//				System.out.println("menu name--"+eMenu.getName());
//			}}
			result.ok(all);
		} catch (Exception e) {
			LOG.debug(e.getMessage(), e);
			result.error(e);
		}
		return result;
	}

	/**
	 * 查询account表的列表，关联出user对象
	 * 
	 * @param queryJs
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ActionResultObj list(@RequestBody SearchQueryJS queryJs) {
		ActionResultObj result = new ActionResultObj();
		try {
			Pager pager = queryJs.toPager();
			WMap query = queryJs.getQuery();

			// 处理查询条件
			Criteria cnd = Cnd.cri();
			cnd.where().andEquals("del_flag", Constans.POS_NEG.NEG);
			if (query.get("orgCode") != null && StringUtil.isNotBlank(query.get("orgCode").toString())) {
				cnd.where().and("org_id", "=", query.get("orgCode").toString());
			}
			if(query.get("userName")!=null && StringUtil.isNotBlank(query.get("userName").toString())){
				System.out.println("usernameqqq:"+query.get("userName").toString());
				String username=query.get("userName").toString().trim();
				cnd.where().and("user_id","in","select id from sys_user where username REGEXP '^"+username+"'");
			}
			
			// 分页查询
			List<EAccount> accountList = accountDao.query(cnd, pager);
			pager.setRecordCount(accountDao.count(cnd));
			for (EAccount eAccount : accountList) {
				EUser user = userDao.fetch(eAccount.getUserId());
				eAccount.setUser(user);
				
				
				//XXX 关联了org的name出来，但是org是不在common下的，所以造成common依赖于org表
				Sql searchOrgSql = Sqls.create("select name from da_org where id=@orgId");
				searchOrgSql.params().set("orgId", eAccount.getOrgId());
				searchOrgSql.setCallback(new SqlCallback() {
					@Override
					public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
						List<String> list = new LinkedList<String>();
						while(rs.next())	{
							list.add(rs.getString("name"));
						}
						return list;
					}
				});
				
				accountDao.dao().execute(searchOrgSql);
				List<String> orgNameList = searchOrgSql.getList(String.class);
				eAccount.setOrgName(orgNameList.size()==0?"":orgNameList.get(0));
			}

			// 处理返回值
			WMap map = new WMap();
			if (queryJs.getQuery() != null) {
				map.putAll(queryJs.getQuery());
			}
			map.put("data", accountList);
			map.put("page", queryJs.toWPage(pager));
			result.ok(map);
			result.okMsg("查询成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

	/**
	 * 新增一个用户，同时增加account和user
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/save")
	public ActionResultObj save(@RequestBody EAccount account) {
		ActionResultObj result = new ActionResultObj();
		try {
			account = userDetailsService.saveUser(account);
			if (account.getId() != null && account.getId() != 0) {
				result.okMsg("保存成功！");
			} else {
				result.errorMsg("保存失败！");
			}
		} catch (Exception e) {
			LOG.error("保存失败，原因：" + e.getMessage());
			result.error(e);
			EUser user=account.getUser();
			if(user.getUsername()!=null&&StringUtil.isNotBlank(user.getUsername())){
				if(userDetailsService.isUserExisted(user.getUsername()))
					result.errorMsg("用户  "+user.getUsername()+" 已存在！");
			}
		}
		return result;
	}

	/**
	 * 得到一个账户的信息，关联出user
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get/{id}")
	public ActionResultObj get(@PathVariable Long id) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (id > 0) {
				List<Long> roleIds = new ArrayList<>();
				EAccount account = accountDao.fetch(id);
				if (account != null) {
					accountDao._fetchLinks(account, "user");
					EUser user=account.getUser();
					if (user != null) {
						user.setPassword(null);
						List<ERole> roles=userDetailsService.getUserRoles(user.getId());
						if(roles!=null){
							for (int i=0;i<roles.size();i++) {
								roleIds.add(roles.get(i).getId());
							}
							user.setRoles(roles);
						}	
					}
					WMap map = new WMap();
					map.put("data", account);
					map.put("roleIds", roleIds);
					result.ok(map);
					result.okMsg("查询成功！");
				} else {
					result.errorMsg("查询失败！");
				}
			} else {
				result.errorMsg("查询失败，链接不存在！");
			}
		} catch (Exception e) {
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

	/**
	 * 根据accountId删除一个账户，同时删除user
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}")
	public ActionResultObj delete(@PathVariable Long id) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (id != 0) {
				userDetailsService.deleteUserByAccountId(id);
				result.okMsg("删除成功！");
			} else {
				result.errorMsg("删除失败，链接不存在！");
			}
		} catch (Exception e) {
			LOG.error("删除失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}
	@RequestMapping(value = "/roleList")
	public ActionResultObj getAllRoleList(){
		ActionResultObj result= new ActionResultObj();
		try{
			Criteria cnd=Cnd.cri();
//			cnd.where().andNotEquals("name", "Admin");
			List<ERole> roles=roleDao.query(cnd);
			WMap map=new WMap();
			map.put("data", roles);
			result.ok(map);
			result.okMsg("查询角色列表成功!");
		}catch( Exception e){
			LOG.error("获取角色列表失败："+e.getMessage());
			result.error(e);
		}
		return result;
	}
}
