package com.springtest.jsondb;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Set;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class JsondbApplicationTests {

	@Test
	public void createUser() throws IOException {
		Set<Users> oldLoads = getLoads();
		Users newUsers = new Users().withName("Test name").withAddress("Test address").withPhone("Test phone");
		long userId = createUsers(newUsers);
		Set<Users> newLoads = getLoads();
		oldLoads.add(newUsers.withId(userId));
		Assert.assertEquals(newLoads, oldLoads);
	}
	private Set<Users> getLoads() throws IOException {
		String json = Request.Get("http://localhost:8080/users/list").execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement list = parsed.getAsJsonObject().get("Object");
		return new Gson().fromJson(list, new TypeToken<Set<Users>>(){}.getType());

	}

	private long createUsers(Users newUsers) throws IOException {
		String json = Request.Post("http://localhost:8080/users/list").
				bodyForm(new BasicNameValuePair("name",newUsers.getName()),
						new BasicNameValuePair("address", newUsers.getAddress()),
						new BasicNameValuePair("phone", newUsers.getPhone())).
				execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		return parsed.getAsJsonObject().get("Id").getAsLong();

	}

}
