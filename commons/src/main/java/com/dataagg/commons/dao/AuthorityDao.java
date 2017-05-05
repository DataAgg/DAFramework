package com.dataagg.commons.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EAuthority;

@Component
public class AuthorityDao extends IdEntityService<EAuthority> {

	@Autowired
	public AuthorityDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public List<EAuthority> getDefaultAuthorities() {
		return Arrays.asList(fetch(1));
	}

	public EAuthority fetchByName(String name) {
		return fetch(Cnd.where("name", "=", name));
	}

	public void insertAll(Map<String, String> allAuthorities) {
		if (allAuthorities != null) {
			for (String name : allAuthorities.keySet()) {
				EAuthority a = fetchByName(name);
				if (a == null) {
					a = new EAuthority();
					a.setName(name);
					a.setDescription(allAuthorities.get(name));
					_insert(a);
				} else {
					a.setName(name);
					a.setDescription(allAuthorities.get(name));
					_update(a);
				}
			}
		}
	}
}
