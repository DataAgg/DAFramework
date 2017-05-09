package com.dataagg.commons.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.dataagg.commons.dao.AccountDao;
import com.dataagg.commons.dao.AuthorityDao;
import com.dataagg.commons.dao.OpenUserDao;
import com.dataagg.commons.dao.RoleDao;
import com.dataagg.commons.dao.UserDao;
import com.dataagg.commons.domain.EAccount;
import com.dataagg.commons.domain.EAuthority;
import com.dataagg.commons.domain.EOpenUser;
import com.dataagg.commons.domain.ERole;
import com.dataagg.commons.domain.EUser;
import com.dataagg.util.Constans;

import jodd.util.StringUtil;

/**
 * Created by samchu on 2017/2/15.
 */
@Service
public class SysUserDetailsService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(SysUserDetailsService.class);
	public final static String DefaultPswd = "12345678";

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private OpenUserDao openUserDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 使用统一加密算法生成加密后的密码
	 *
	 * @param password
	 * @return
	 */
	public String genPassword(String password) {
		return passwordEncoder.encode(password);
	}

	/**
	 * 根据用户名获取UserDetails 默认接口实现方法
	 *
	 */
	@Override
	@NotNull
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		log.debug("loadUserByUsername:{}", userName);
		EUser user = fetchByName(userName);
		if (user == null) { throw new UsernameNotFoundException(userName); }
		return user;
	}

	/**
	 * 根据用户名获取用户基本信息
	 *
	 * @param userName
	 * @return
	 */
	@Null
	public EUser fetchByName(String userName) {
		return userDao.fetchByName(userName);
	}

	/**
	 * 更新user里的roles和authorities
	 *
	 * @param user
	 */
	public void updateAuthorities(EUser user) {
		userDao._fetchLinks(user, "roles");
		List<EAuthority> authorities = new ArrayList<>();
		if (user.getRoles() != null) {
			for (ERole role : user.getRoles()) {
				roleDao._fetchLinks(role, "authorities");
				if (role.getAuthorities() != null) {
					authorities.addAll(role.getAuthorities());
				}
			}
		}
		user.setGrantedAuthorities(authorities);
	}

	/**
	 * 根据用户名获取用户的全部信息,包含角色和权限
	 *
	 * @param userName
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public EUser fetchFullByName(String userName) {
		EUser userDetails = fetchByName(userName);
		updateAuthorities(userDetails);
		return userDetails;
	}

	/**
	 * 根据principal获取当前用户所有Authorities
	 *
	 * @param principal
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public Set<String> principalAuthorities(Principal principal) {
		Set<String> userAuthorities = new HashSet<>();
		if (principal != null && principal.getName() != null) {
			userAuthorities.addAll(fetchUserAuthorities(principal.getName()));
		} else {
			userAuthorities.add("guest");
		}
		return userAuthorities;
	}

	/**
	 * 根据用户名获取用户的权限标识
	 *
	 * @param userName
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public Set<String> fetchUserAuthorities(String userName) {
		Set<String> userAuthorities = new HashSet<>();
		EUser user = fetchFullByName(userName);
		if (user != null && user.getAuthorities() != null) {
			for (EAuthority a : user.getAuthorities()) {
				userAuthorities.add(a.getAuthority());
			}
		} else {
			userAuthorities.add("guest");
		}
		return userAuthorities;
	}

	/**
	 * 创建用户
	 *
	 * @param user
	 * @return
	 */
	public EUser create(EUser user) {
		EUser existing = userDao.fetchByName(user.getUsername());
		Assert.isNull(existing, "user already exists: " + user.getUsername());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(roleDao.getDefaultRoles());
		EUser newUser = userDao._insert(user);
		// ccount.setUser(newUser);
		userDao._insertRelation(user, "roles");
		// accountDao._insertLinks(account,"user");
		log.info("new user has been created: {}-{}", user.getUsername(), newUser.getId());
		return newUser;
	}

	/**
	 * 根据当前用户信息获取当前用户
	 *
	 * @param openUser
	 * @return
	 */
	public EUser checkAndCreate(EOpenUser openUser) {
		openUser = openUserDao.createOrFind(openUser);
		EUser user = null;
		if (openUser != null && openUser.getUserId() == null) {
			user = userDao.fetchByName(openUser.getClientName());
			if (user == null) {
				user = new EUser();
				user.setUsername(openUser.getClientName());
				user.setPassword(DefaultPswd);
				user = create(user);
				openUser.setUserId(user.getId());
				openUserDao._update(openUser);
			} else {
				log.warn("当前用户名已经被人占用!");
			}
		} else if (openUser != null) {
			user = userDao.fetch(openUser.getUserId());
		}
		return user;
	}

	/**
	 * 重新设置角色对应的权限信息
	 *
	 * @param roleName
	 * @param allAuthorities
	 */
	public void updateRoleAuthorities(String roleName, String... allAuthorities) {
		if (allAuthorities != null) {
			String queryStr = "";
			for (String a : allAuthorities) {
				queryStr += "'" + a + "',";
			}
			queryStr = StringUtil.cutSuffix(queryStr, ",");
			List<EAuthority> authorities = authorityDao.query(Cnd.where("name", "in", queryStr));
			ERole role = roleDao.fetchByName(roleName);
			role.setAuthorities(authorities);
			roleDao.updateAuthorities(role);
		}
	}

	/**
	 * 初始化admin role和admin user的权限信息
	 */
	public void initAdminData() {
		// init role and users
		ERole adminRole = roleDao.fetch(1L);
		if (adminRole == null) {
			adminRole = new ERole();
		}
		adminRole.setId(1L);
		adminRole.setName("Admin");
		adminRole.setDescription("系统管理员");
		adminRole = roleDao.save(adminRole);
		Assert.isTrue(adminRole.getId() == 1L, "");

		// insert all authorities into admin Role
		List<EAuthority> allAuthorities = authorityDao.query();
		adminRole.setAuthorities(allAuthorities);
		roleDao.updateAuthorities(adminRole);

		// init admin user
		String adminUserName = "watano";
		EUser adminUser = userDao.fetch(1L);
		if (adminUser == null) {
			adminUser = new EUser();
			adminUser.setId(1L);
			adminUser.setOrgId(1L);
			adminUser.setUsername(adminUserName);
			adminUser.setPassword(genPassword("password"));
			userDao._insert(adminUser);
		}
		// insert all role
		List<ERole> roles = new ArrayList<>();
		roles.add(adminRole);
		adminUser.setRoles(roles);
		userDao.updateRoles(adminUser);
	}

	/**
	 * 新增账户信息和用户
	 *
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public EAccount saveUser(EAccount account) throws Exception {
		EUser user = account.getUser();
		//		EUser user = userDao.fetch(account.getUserId());
		List<Long> roleList = account.getRoleList();
		List<ERole> rolesInsert = null;
		if (roleList != null && roleList.size() > 0) {
			rolesInsert = getInsertUserRoles(roleList);
		}
		List<ERole> rolesDB = getUserRoles(account.getUserId());
		if (account.getId() != null && account.getId() != 0) {
			// 修改
			user.setId(account.getUserId());
			user.setOrgId(account.getOrgId());
			// 新密码不为空，生成新密码；为空，取数据库原密码设值
			if (user.getPassword() != null && !user.getPassword().equals("")) {
				user.setPassword(genPassword(user.getPassword()));
			} else {
				EUser userDb = userDao.fetch(user.getId());
				if (userDb != null) {
					user.setPassword(userDb.getPassword());
				}
			}
			userDao._update(user);
			accountDao._update(account);
		} else {
			user.setOrgId(account.getOrgId());
			user.setPassword(genPassword(user.getPassword()));
			userDao._insert(user);
			account.setUserId(user.getId());
			account = accountDao._insert(account);
		}
		// 删除该用户关联记录
		if (rolesDB != null && rolesDB.size() > 0) {
			user.setRoles(rolesDB);
			userDao._clearLinks(user, "roles");
		}
		// insert全新关联记录
		if (rolesInsert != null && rolesInsert.size() > 0) {
			user.setRoles(rolesInsert);
			userDao._insertRelation(user, "roles");
		}
		return account;
	}

	/**
	 * 根据roleList列表，获取需要insert 到sys_user_role的List对象
	 * @param roleList
	 * @return
	 */
	private List<ERole> getInsertUserRoles(List<Long> roleList) {
		List<ERole> roles = new ArrayList<>();
		for (Long roleId : roleList) {
			ERole role = roleDao.fetch(roleId);
			if (role != null) {
				roles.add(role);
			}
		}
		return roles;
	}

	/**
	 * 根据account的ID，删除account和user
	 *
	 * 不做物理删除，将删除标记设置为1，update
	 * @param account
	 * @throws Exception
	 */
	public void deleteUserByAccountId(Long accountId) throws Exception {
		//		Long userId = accountDao.fetch(accountId).getUserId();
		EAccount account = accountDao.fetch(accountId);
		Long userId = account.getUserId();
		EUser user = userDao.fetch(userId);
		account.setDelFlag(Constans.POS_NEG.POS);
		int flag1 = accountDao._update(account);
		if (flag1 != 1) { throw new Exception("删除account用户失败"); }
		user.setDelFlag(Constans.POS_NEG.POS);
		int flag2 = userDao._update(user);
		if (flag2 != 1) { throw new Exception("删除user失败"); }
	}

	/**
	 * 根据用户ID，获取用户对应的角色列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ERole> getUserRoles(Long userId) throws Exception {
		List<ERole> userRoles = null;
		if (userId != null) {
			Criteria cnd = Cnd.cri();
			cnd.where().and("id", "in", "select roleid from sys_user_role where userid=" + userId + "");
			userRoles = roleDao.query(cnd);
		}
		return userRoles;
	}

	/**
	 * 判断具体用户是否存在
	 * @param userName
	 * @return
	 */
	public boolean isUserExisted(String userName) {
		EUser user = userDao.fetch(Cnd.where("username", "=", userName));
		return user == null ? false : true;

	}
}
