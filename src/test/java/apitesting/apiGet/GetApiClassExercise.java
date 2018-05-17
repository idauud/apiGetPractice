package apitesting.apiGet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestUtils;

public class GetApiClassExercise {
	String url = "https://reqres.in//api/users/2";
	CloseableHttpResponse closeabelHttpResponse;
	
	@Test(priority=0)

	public void get() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		
		
		HashMap<String , String > header = new HashMap<String , String>();
		header.put("Content-Type", "application/json");
		header.put("billa", "billi");
		
		System.out.println(header);
		for(Entry<String, String> entry : header.entrySet()) {
			
			httpGet.addHeader(entry.getKey(), entry.getValue());
			
		}
		

		closeabelHttpResponse = httpClient.execute(httpGet);
		
		int status =closeabelHttpResponse.getStatusLine().getStatusCode();
		
		System.out.println("status is : "+ closeabelHttpResponse.getStatusLine().getStatusCode());
		
		Assert.assertEquals(status, 200);
		 Header[] allHeaders = closeabelHttpResponse.getAllHeaders();
		 
		 // converting array of headers into 
		 HashMap<String , String> headers = new HashMap<String, String>();
		 
		 for(Header head:allHeaders) {
			 
			 headers.put(head.getName(), head.getValue());
		 }
		 
		System.out.println("header values are::"+headers);
		 
		 String contectType = headers.get("Content-Type");
		 System.out.println(headers.get("Date"));
		 
		 Assert.assertTrue(contectType.contains("application/json"));
	}
	
	@Test(priority=1)
	
	public void entityTest() throws ParseException, IOException {
		
		String responseString = EntityUtils.toString(closeabelHttpResponse.getEntity(), "UTF-8");
		JSONObject jsonObject = new JSONObject(responseString);
		System.out.println(jsonObject);
		
		String id = TestUtils.getValeByJpath(jsonObject, "data/id");
		
		System.out.println(TestUtils.getValeByJpath(jsonObject, "data/first_name"));
		Assert.assertEquals(id, "2");
		
		
		
	}

}
