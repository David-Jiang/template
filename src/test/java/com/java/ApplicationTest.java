package com.java;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.java.config.Application;
import com.java.entity.OrderEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApplicationTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private Gson gson = new GsonBuilder().create();

	@Before
	public void setupMockMvc() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void test() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/saveEntity")).andReturn();
		MvcResult result = mockMvc
				.perform(
							MockMvcRequestBuilders.get("/api/repositoryTest").accept(MediaType.APPLICATION_JSON_UTF8)
						)
						//.contentType(MediaType.APPLICATION_JSON_UTF8).content(gson.toJson(deltaVO)))
				.andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		List<OrderEntity> list = gson.fromJson(jsonResponse, new TypeToken<List<OrderEntity>>(){}.getType());
		list.forEach(v -> {
			System.out.println(v.getOrderNumber());
		});
		Assert.assertTrue(200 == result.getResponse().getStatus());
	}
}
