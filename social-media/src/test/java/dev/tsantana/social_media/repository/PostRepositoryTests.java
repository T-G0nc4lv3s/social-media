package dev.tsantana.social_media.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.tsantana.social_media.domain.Post;
import dev.tsantana.social_media.tests.Factory;

@DataJpaTest
public class PostRepositoryTests {

	@Autowired
	private PostRepository postRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalPosts;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalPosts = 4L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		postRepository.deleteById(existingId);

		Optional<Post> result = postRepository.findById(existingId);

		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldDoNothingWhenIdNonExists() {
		Assertions.assertDoesNotThrow(() -> {
			postRepository.deleteById(existingId);
		});
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {

		Post post = Factory.createPost();
		post = postRepository.save(post);

		Assertions.assertNotNull(post.getId());
		Assertions.assertEquals(countTotalPosts + 1, post.getId());
	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

		Optional<Post> result = postRepository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExists() {

		Optional<Post> result = postRepository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
}
