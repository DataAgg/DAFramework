package com.dataagg.commons.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import com.dataagg.commons.domain.EOrg;
import com.dataagg.commons.service.OrgService;
import com.dataagg.util.SearchQueryJS;
import com.dataagg.util.WMap;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServiceApplication.class)
@WebAppConfiguration
public class OrgControllerTest {

	private static final ObjectMapper mapper = new ObjectMapper();

	@InjectMocks
	private OrgController orgController;

	@Autowired
	private WebApplicationContext webApplicationConnect;
	@Autowired
	private OrgService orgService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
	}

	//	@Test
	public void save() throws Exception {
		final EOrg org = new EOrg();
		org.setName("测试项目" + System.currentTimeMillis());
		org.setCode("001");
		org.setCode("10000");
		orgService.save(org);

		String json = mapper.writeValueAsString(org);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/org/save").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status + "--" + content);
	}

	@Test
	public void get() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/3").accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status + "--" + content);
	}

	@Test
	public void page() throws Exception {
		SearchQueryJS queryJs = new SearchQueryJS();
		WMap query = new WMap();
		query.put("name", "83945332");
		query.put("code", "002");
		queryJs.setQuery(query);
		String json = mapper.writeValueAsString(queryJs);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/org/list").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status + "--" + content);
	}

}
