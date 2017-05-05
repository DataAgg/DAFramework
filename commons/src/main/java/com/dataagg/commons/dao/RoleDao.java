package com.dataagg.commons.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.ERole;

/**
 * Created by watano on 2017/3/13.
 */
@Component
public class RoleDao extends IdEntityService<ERole> {
	@Autowired
	public RoleDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public List<ERole> getDefaultRoles() {
		return Arrays.asList(fetch(1));
	}

	public ERole fetchByName(String name) {
		return fetch(Cnd.where("name", "=", name));
	}

	public ERole fetchFullByName(String name) {
		ERole role = fetchByName(name);
		return _fetchLinks(role, "authorities");
	}

	public ERole save(ERole role) {
		Long newId = role.getId();
		if (newId != null) {
			ERole tmp = fetch(newId);
			if (tmp != null) {
				_update(role);
				return role;
			}
			//FIXME 插入是不会使用设置过的id,始终自动生成:(
			role = _insert(role);
			update(Chain.make("id", newId), Cnd.where("id", "=", role.getId()));
			role.setId(newId);
			return role;
		}
		return _insert(role);
	}

	public void insertAll(Map<String, String> allAoles) {
		if (allAoles != null) {
			for (String name : allAoles.keySet()) {
				ERole a = fetchByName(name);
				if (a == null) {
					a = new ERole();
					a.setName(name);
					a.setDescription(allAoles.get(name));
					_insert(a);
				} else {
					a.setName(name);
					a.setDescription(allAoles.get(name));
					_update(a);
				}
			}
		}
	}

	//FIXME pls polish this codes
	public ERole updateAuthorities(ERole vo) {
		dao().execute(Sqls.create("delete from sys_role_authority where role_id=" + vo.getId()));
		return _insertRelation(vo, "authorities");
	}
}
