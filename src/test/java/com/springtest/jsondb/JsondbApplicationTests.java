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
		int userId = createUsers(newUsers);
		Set<Users> newLoads = getLoads();
		oldLoads.add(newUsers.withId(userId));
		Assert.assertEquals(newLoads, oldLoads);
	}
	private Set<Users> getLoads() throws IOException {
		String json = Request.Get("http://localhost:8080/users/list").execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		return new Gson().fromJson(parsed, new TypeToken<Set<Users>>(){}.getType());

	}

	private int createUsers(Users newUsers) throws IOException {
		String json = Request.Put("http://localhost:8080/users/list").
			bodyForm(new BasicNameValuePair("name",newUsers.getName()),
				new BasicNameValuePair("address", newUsers.getAddress()),
				new BasicNameValuePair("phone", newUsers.getPhone())).
				execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		return parsed.getAsJsonObject().get("Id").getAsInt();
	}

	@Test
	public void updateUser() throws IOException {
		Set<Users> oldLoads = getLoads();
		Users modUsers = new Users().withId(oldLoads.size()).withName("Update name").
				withAddress("Update address").withPhone("Update phone");
		Request.Post("http://localhost:8080/users/list").
				bodyForm(new BasicNameValuePair("Id", Integer.toString(oldLoads.size() - 1)),
						new BasicNameValuePair("name",modUsers.getName()),
						new BasicNameValuePair("address", modUsers.getAddress()),
						new BasicNameValuePair("phone", modUsers.getPhone())).
				execute().returnContent().asString();
		Set<Users> newLoads = getLoads();
		Assert.assertEquals(newLoads, oldLoads);
	}

	@Test
	public void deleteUser() throws IOException {
		Set<Users> oldLoads = getLoads();
		Users delUsers = new Users().withId(oldLoads.size()).withName(null).
				withAddress(null).withPhone(null);
		Request.Delete("http://localhost:8080/users/list").
				bodyForm(new BasicNameValuePair("Id", Integer.toString(oldLoads.size() - 1)),
						new BasicNameValuePair("name",delUsers.getName()),
						new BasicNameValuePair("address", delUsers.getAddress()),
						new BasicNameValuePair("phone", delUsers.getPhone())).
				execute().returnContent().asString();
		Set<Users> newLoads = getLoads();
		oldLoads.remove(delUsers);
		Assert.assertEquals(newLoads, oldLoads);
	}

	@Test
	public void readUser() throws IOException {
		Set<Users> oldLoads = getLoads();
		Users readUsers = new Users().withId(oldLoads.size()).withName(null).
				withAddress(null).withPhone(null);
		Request.Get("http://localhost:8080/users/list").
				bodyForm(new BasicNameValuePair("Id", Integer.toString(oldLoads.size() - 1)),
						new BasicNameValuePair("name",readUsers.getName()),
						new BasicNameValuePair("address", readUsers.getAddress()),
						new BasicNameValuePair("phone", readUsers.getPhone())).
				execute().returnContent().asString();
	}
}
