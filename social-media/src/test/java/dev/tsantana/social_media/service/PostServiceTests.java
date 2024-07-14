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

import dev.tsantana.social_media.DTO.InsertPostDTO;
import dev.tsantana.social_media.domain.Post;
import dev.tsantana.social_media.repository.PostRepository;
import dev.tsantana.social_media.tests.Factory;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class PostServiceTests {

	@InjectMocks
	private PostServiceImpl postService;

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserServiceImpl userService;

	private Long existingId;
	private Long nonExistingId;
	private PageImpl<Post> page;
	private Post post;
	private InsertPostDTO insertDTO;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		post = Factory.createPost();
		insertDTO = Factory.createPostDTO();
		page = new PageImpl<>(List.of(post));

		// Configurando comportamento do PostRepository Mock

		Mockito.when(postRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(postRepository.save(ArgumentMatchers.any())).thenReturn(post);
		Mockito.when(postRepository.findById(existingId)).thenReturn(Optional.of(post));
		Mockito.when(postRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(postRepository.getReferenceById(existingId)).thenReturn(post);
		Mockito.when(postRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.doNothing().when(postRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(postRepository).deleteById(nonExistingId);

		// Configurando comportamento do UserService Mock

		Mockito.when(userService.findById(existingId)).thenReturn(post.getUser());
		Mockito.when(userService.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	}

	@Test
	public void insertShouldReturnPostWhenDTOIsValid() {

		post = postService.insert(insertDTO);

		Assertions.assertNotNull(post);

		Mockito.verify(userService, Mockito.times(1)).findById(insertDTO.getUserId());
	}

	@Test
	public void updateShouldReturnPostWhenIdExists() {

		post = postService.update(existingId, insertDTO);

		Assertions.assertNotNull(post);

	}

	@Test
	public void updateShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			postService.update(nonExistingId, insertDTO);
		});

	}

	@Test
	public void findByIdShouldReturnPostWhenIdExists() {

		post = postService.findById(existingId);

		Assertions.assertNotNull(post);
	}

	@Test
	public void deleteByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			postService.deleteById(nonExistingId);
		});

		Mockito.verify(postRepository, Mockito.times(1)).deleteById(nonExistingId);

	}

	@Test
	public void deleteByIdShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			postService.deleteById(existingId);
		});

		Mockito.verify(postRepository, Mockito.times(1)).deleteById(existingId);
	}

	@Test
	public void findByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			postService.findById(nonExistingId);
		});
	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable page = PageRequest.of(0, 10);

		Page<Post> result = postService.findAllPaged(page);

		Assertions.assertNotNull(result);
		Mockito.verify(postRepository, Mockito.times(1)).findAll(page);
	}

}
