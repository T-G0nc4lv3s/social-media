package dev.tsantana.social_media.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.tsantana.social_media.DTO.InsertAlbumDTO;
import dev.tsantana.social_media.domain.Album;
import dev.tsantana.social_media.repository.AlbumRepository;
import dev.tsantana.social_media.tests.Factory;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class AlbumServiceTests {

	@InjectMocks
	private AlbumServiceImpl albumService;

	@Mock
	private AlbumRepository albumRepository;

	@Mock
	private UserServiceImpl userService;

	private Long existingId;
	private Long nonExistingId;
	private PageImpl<Album> page;
	private Album album;
	private InsertAlbumDTO insertDTO;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		album = Factory.createAlbum();
		insertDTO = Factory.createAlbumDTO();
		page = new PageImpl<>(List.of(album));

		// Configurando comportamento do PostRepository Mock

		Mockito.when(albumRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(albumRepository.save(ArgumentMatchers.any())).thenReturn(album);
		Mockito.when(albumRepository.findById(existingId)).thenReturn(Optional.of(album));
		Mockito.when(albumRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(albumRepository.getReferenceById(existingId)).thenReturn(album);
		Mockito.when(albumRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.doNothing().when(albumRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(albumRepository).deleteById(nonExistingId);

		// Configurando comportamento do UserService Mock

		Mockito.when(userService.findById(existingId)).thenReturn(album.getUser());
		Mockito.when(userService.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	}

	@Test
	public void insertShouldReturnAlbumWhenDTOIsValid() {

		album = albumService.insert(insertDTO);

		Assertions.assertNotNull(album);

		Mockito.verify(userService, Mockito.times(1)).findById(insertDTO.getUserId());
	}

	@Test
	public void updateShouldReturnPostWhenIdExists() {

		album = albumService.update(existingId, insertDTO);

		Assertions.assertNotNull(album);

	}

	@Test
	public void updateShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			albumService.update(nonExistingId, insertDTO);
		});

	}

	@Test
	public void findByIdShouldReturnAlbumWhenIdExists() {

		album = albumService.findById(existingId);

		Assertions.assertNotNull(album);
	}

	@Test
	public void deleteByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			albumService.deleteById(nonExistingId);
		});

		Mockito.verify(albumRepository, Mockito.times(1)).deleteById(nonExistingId);

	}

	@Test
	public void deleteByIdShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			albumService.deleteById(existingId);
		});

		Mockito.verify(albumRepository, Mockito.times(1)).deleteById(existingId);
	}

	@Test
	public void findByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			albumService.findById(nonExistingId);
		});
	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable page = PageRequest.of(0, 10);

		Page<Album> result = albumService.findAllPaged(page);

		Assertions.assertNotNull(result);
		Mockito.verify(albumRepository, Mockito.times(1)).findAll(page);
	}

}
