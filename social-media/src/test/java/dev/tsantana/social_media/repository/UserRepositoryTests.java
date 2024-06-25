package dev.tsantana.social_media.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.tsantana.social_media.domain.User;
import dev.tsantana.social_media.tests.Factory;

@DataJpaTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalUsers;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalUsers = 6L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(existingId);

		Optional<User> result = repository.findById(existingId);

		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldDoNothingWhenIdNonExists() {
		Assertions.assertDoesNotThrow(() -> {
			repository.deleteById(existingId);
		});
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {

		User user = Factory.createUser();
		user = repository.save(user);

		Assertions.assertNotNull(user.getId());
		Assertions.assertEquals(countTotalUsers + 1, user.getId());
	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

		Optional<User> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExists() {

		Optional<User> result = repository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
}
