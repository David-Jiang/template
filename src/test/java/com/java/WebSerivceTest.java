package com.java;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.java.config.Application;
import com.java.entity.OrderEntity;
import com.java.util.HttpService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class WebSerivceTest {

	private Gson gson = new GsonBuilder().create();
	
	@Test
	public void test() throws Exception {
		String jsonResponse = HttpService.callHttpGet("http://localhost:8000/api/repositoryTest");
		List<OrderEntity> list = gson.fromJson(jsonResponse, new TypeToken<List<OrderEntity>>(){}.getType());
		list.forEach(v -> {
			System.out.println(v.getOrderNumber());
		});
		Assert.assertNotNull(jsonResponse);
	}
}
