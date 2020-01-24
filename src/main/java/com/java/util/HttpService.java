package com.java.util;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpService {
	
	public static String callHttpGet(String url) throws Exception {
		return callHttpByMethod(HttpMethod.GET.name(), url, null);
	}
	
	public static String callHttpPost(String url, String postBodyJson) throws Exception {
		return callHttpByMethod(HttpMethod.POST.name(), url, postBodyJson);
	}
	
	private static String callHttpByMethod(String httpMethod, String url, String postBodyJson) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		String jsonResponse = null;
		try {
			if (Objects.equals(HttpMethod.GET.name(), httpMethod)) {
				HttpGet httpGet = new HttpGet(url);
				httpGet.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
				httpGet.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
				httpResponse = httpClient.execute(httpGet);
			} else if (Objects.equals(HttpMethod.POST.name(), httpMethod)) {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
				httpPost.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
				if (StringUtils.isNotBlank(postBodyJson)) {
					httpPost.setEntity(new StringEntity(postBodyJson));
				}
				httpResponse = httpClient.execute(httpPost);
			} else {
				return null;
			}
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode >= 200 && statusCode < 300) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					jsonResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
				}
			} else {
				log.error("callHttpByMethod response status code not equal 2xx ");
				throw new Exception("callHttpByMethod response status code not equal 2xx");
			}
		} catch (Exception e) {
			log.error("callHttpByMethod occur error reason : " + e);
			throw e;
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception e) {
				log.error("httpResponse httpClient close error reason : " + e);
				throw e;
			}
		}
		return jsonResponse;
	}
}
