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

import dev.tsantana.social_media.DTO.InsertPhotoDTO;
import dev.tsantana.social_media.domain.Photo;
import dev.tsantana.social_media.repository.AlbumRepository;
import dev.tsantana.social_media.repository.PhotoRepository;
import dev.tsantana.social_media.repository.PostRepository;
import dev.tsantana.social_media.repository.UserRepository;
import dev.tsantana.social_media.tests.Factory;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class PhotoServiceTests {

	@InjectMocks
	private PhotoServiceImpl photoService;

	@Mock
	private PhotoRepository photoRepository;

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AlbumRepository albumRepository;

	private Long existingId;
	private Long nonExistingId;
	private PageImpl<Photo> page;
	private Photo photo;
	private InsertPhotoDTO insertDTO;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		photo = Factory.createPhoto();
		insertDTO = Factory.createPhotoDTO();
		page = new PageImpl<>(List.of(photo));

		Mockito.when(photoRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(photoRepository.save(ArgumentMatchers.any())).thenReturn(photo);
		Mockito.when(photoRepository.findById(existingId)).thenReturn(Optional.of(photo));
		Mockito.when(photoRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(photoRepository.getReferenceById(existingId)).thenReturn(photo);
		Mockito.when(photoRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.doNothing().when(photoRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(photoRepository).deleteById(nonExistingId);

		Mockito.when(userRepository.findById(existingId)).thenReturn(Optional.of(photo.getUser()));

	}

	@Test
	public void insertShouldReturnPhotoWhenDTOIsValid() {

		photo = photoService.insert(insertDTO);

		Assertions.assertNotNull(photo);

		Mockito.verify(userRepository, Mockito.times(1)).findById(existingId);
	}

	@Test
	public void updateShouldReturnPhotoWhenIdExists() {

		photo = photoService.update(existingId, insertDTO);

		Assertions.assertNotNull(photo);

	}

	@Test
	public void updateShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			photoService.update(nonExistingId, insertDTO);
		});

	}

	@Test
	public void findByIdShouldReturnPhotoWhenIdExists() {

		photo = photoService.findById(existingId);

		Assertions.assertNotNull(photo);
	}

	@Test
	public void deleteByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			photoService.deleteById(nonExistingId);
		});

		Mockito.verify(photoRepository, Mockito.times(1)).deleteById(nonExistingId);

	}

	@Test
	public void deleteByIdShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			photoService.deleteById(existingId);
		});

		Mockito.verify(photoRepository, Mockito.times(1)).deleteById(existingId);
	}

	@Test
	public void findByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			photoService.findById(nonExistingId);
		});
	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable page = PageRequest.of(0, 10);

		Page<Photo> result = photoService.findAllPaged(page);

		Assertions.assertNotNull(result);
		Mockito.verify(photoRepository, Mockito.times(1)).findAll(page);
	}
}
