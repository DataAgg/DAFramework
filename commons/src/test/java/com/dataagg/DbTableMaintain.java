package com.dataagg;

import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.dao.util.Daos;

/**
 * Created by watano on 2017/3/1.
 */
public class DbTableMaintain {
	public static void main(String[] args) {
		try {
			SimpleDataSource ds = new SimpleDataSource();
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/dataagg?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
			ds.setUsername("dataagg");
			ds.setPassword("EdIyNje2GsRyzPDXQfp1");
			NutDao dao = new NutDao(ds);
			Daos.createTablesInPackage(dao, "com.dataagg.commons.domain", false);
			Daos.migration(dao, "com.dataagg.commons.domain", true, true, true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
