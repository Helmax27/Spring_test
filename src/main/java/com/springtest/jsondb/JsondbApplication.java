package com.springtest.jsondb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtest.jsondb.domain.User;
import com.springtest.jsondb.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class JsondbApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsondbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserService userService) {
		return args -> {
			// read json and wwrite to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
			try {
				//List<User> listStrings = new ArrayList<User>();
				//User u1 = new User();
				//u1.id= new Long(111);
				//u1.name="nnnnn";
				//u1.address="nnnnn";
				//u1.phone="nnnnn";
				//listStrings.add(u1);
				//String b = mapper.writeValueAsString(listStrings);
				List<User> users = mapper.readValue(inputStream, typeReference);
				userService.save(users);
				System.out.println("Users Saved!");
			} catch (IOException e) {
				System.out.println("Unable to save users: " + e.getMessage());
			}

		};
	}
}
