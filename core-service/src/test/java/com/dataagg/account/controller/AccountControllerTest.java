package com.dataagg.account.controller;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dataagg.CoreServiceApplication;
import com.dataagg.commons.domain.EAccount;
import com.dataagg.util.SearchQueryJS;
import com.dataagg.util.WMap;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServiceApplication.class)
@WebAppConfiguration
public class AccountControllerTest {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext webApplicationConnect;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
	}

	@Test
	public void get() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/account/get/1").accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status + "--" + content);
	}

	@Test
	public void page() throws Exception {
		SearchQueryJS queryJs = new SearchQueryJS();
		WMap map = new WMap();
		map.put("full_name", "名");
		queryJs.setQuery(map);

		String json = mapper.writeValueAsString(queryJs);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/account/list").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status + "--" + content);
	}

	//	@Test
	public void save() throws Exception {
		EAccount account = new EAccount();
		account.setFullName("用户全名");
		account.setMobile("13987725457");
		account.setEmail("362527240@qq.com");
		account.setAddress("大帝国");
		account.setComment("这是备注" + new Date());
		account.setUserId(1L);
		account.setOrgId(1L);
		account.setNumber("530404199007777777");

		String json = mapper.writeValueAsString(account);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/account/save").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status + "--" + content);
	}

}
