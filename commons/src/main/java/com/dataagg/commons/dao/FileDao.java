package com.dataagg.commons.dao;

import com.dataagg.commons.domain.EFile;

import javax.sql.DataSource;

import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 附件管理DAO
 * Created by carlos on 2017/3/29.
 */
@Component
public class FileDao extends IdEntityService<EFile> {

	@Autowired
	public FileDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
	
}
