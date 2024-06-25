package dev.tsantana.social_media.tests;

import java.time.LocalDate;

import dev.tsantana.social_media.domain.User;

public class Factory {

	public static User createUser() {
		User user = new User(null, "Bob", "bob@gmail.com",
				LocalDate.of(1998, 05, 11), "male", null);
		return user;
	}
}
