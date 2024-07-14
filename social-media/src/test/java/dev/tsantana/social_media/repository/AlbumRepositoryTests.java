package dev.tsantana.social_media.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.tsantana.social_media.domain.Album;
import dev.tsantana.social_media.tests.Factory;

@DataJpaTest
public class AlbumRepositoryTests {

	@Autowired
	private AlbumRepository albumRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalAlbuns;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalAlbuns = 1L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		albumRepository.deleteById(existingId);

		Optional<Album> result = albumRepository.findById(existingId);

		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldDoNothingWhenIdNonExists() {
		Assertions.assertDoesNotThrow(() -> {
			albumRepository.deleteById(existingId);
		});
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {

		Album album = Factory.createAlbum();
		album = albumRepository.save(album);

		Assertions.assertNotNull(album.getId());
		Assertions.assertEquals(countTotalAlbuns + 1, album.getId());
	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

		Optional<Album> result = albumRepository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNonExists() {

		Optional<Album> result = albumRepository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
}
