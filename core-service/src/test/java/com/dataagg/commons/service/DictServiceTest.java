package com.dataagg.commons.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dataagg.CoreServiceApplication;
import com.dataagg.commons.dao.DictDao;
import com.dataagg.commons.domain.EDict;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServiceApplication.class)
public class DictServiceTest {
	@Autowired
	private DictDao dictDao;

	@Test
	public void test() {}

	public void add(String[][] dictList) {
		EDict d = null;
		int i = 1;
		for (String[] dict : dictList) {
			d = new EDict();
			d.setType(dict[0]);
			d.setValue(dict[1]);
			d.setLabel(dict[2]);
			d.setDescription(dict[3]);
			d.setSort(i);
			i++;
			dictDao._insert(d);
		}
	}
}
