package dev.tsantana.social_media.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dev.tsantana.social_media.domain.User;
import dev.tsantana.social_media.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User insert(User user) {
		User result = userRepository.save(user);
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		List<User> result = userRepository.findAll();
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<User> findAllPaged(Pageable pageable) {
		Page<User> result = userRepository.findAll(pageable);
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public User findById(Long userId) {
		User entity = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("Entity not found."));
		return entity;
	}

	@Override
	public User update(Long userId, User user) {
		User entity = new User();
		try {
			entity = userRepository.getReferenceById(userId);
			user.setId(entity.getId());
			BeanUtils.copyProperties(user, entity);
			entity = userRepository.save(user);
			return entity;
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Id not found: " + userId);
		}

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteById(Long userId) {
		try {
			userRepository.deleteById(userId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Id not found: " + userId);
		}

	}

}
