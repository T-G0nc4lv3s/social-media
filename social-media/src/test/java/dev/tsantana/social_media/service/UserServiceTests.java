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

import dev.tsantana.social_media.domain.User;
import dev.tsantana.social_media.repository.UserRepository;
import dev.tsantana.social_media.tests.Factory;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserRepository userRepository;

	private Long existingId;
	private Long nonExistingId;
	private PageImpl<User> page;
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		user = Factory.createUser();
		page = new PageImpl<>(List.of(user));

		// Configurando comportamento do UserRepository Mock

		Mockito.when(userRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
		Mockito.when(userRepository.findById(existingId)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(userRepository.getReferenceById(existingId)).thenReturn(user);
		Mockito.when(userRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.doNothing().when(userRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(userRepository).deleteById(nonExistingId);

	}

	@Test
	public void updateShouldReturnUserWhenIdExists() {

		user = service.update(existingId, user);

		Assertions.assertNotNull(user);
	}

	@Test
	public void updateShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.update(nonExistingId, user);
		});
	}

	@Test
	public void findByIdShouldReturnUserWhenIdExists() {

		user = service.findById(existingId);

		Assertions.assertNotNull(user);
	}

	@Test
	public void findByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable page = PageRequest.of(0, 10);

		Page<User> result = service.findAllPaged(page);

		Assertions.assertNotNull(result);
		Mockito.verify(userRepository, Mockito.times(1)).findAll(page);
	}

	@Test
	public void deleteByIdShouldThrowEntityNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.deleteById(nonExistingId);
		});

		// verifica se foi chamado algum método do mock
		Mockito.verify(userRepository, Mockito.times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteByIdShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.deleteById(existingId);
		});

		Mockito.verify(userRepository, Mockito.times(1)).deleteById(existingId);
	}
}
