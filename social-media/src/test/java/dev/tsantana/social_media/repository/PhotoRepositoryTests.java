package dev.tsantana.social_media.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.tsantana.social_media.domain.Photo;
import dev.tsantana.social_media.tests.Factory;

@DataJpaTest
public class PhotoRepositoryTests {

	@Autowired
	private PhotoRepository photoRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalPhotos;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalPhotos = 4L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		photoRepository.deleteById(existingId);

		Optional<Photo> result = photoRepository.findById(existingId);

		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldDoNothingWhenIdNonExists() {
		Assertions.assertDoesNotThrow(() -> {
			photoRepository.deleteById(existingId);
		});
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {

		Photo photo = Factory.createPhoto();
		photo = photoRepository.save(photo);

		Assertions.assertNotNull(photo.getId());
		Assertions.assertEquals(countTotalPhotos + 1, photo.getId());
	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

		Optional<Photo> result = photoRepository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExists() {

		Optional<Photo> result = photoRepository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
}
