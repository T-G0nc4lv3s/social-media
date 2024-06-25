package dev.tsantana.social_media.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.tsantana.social_media.domain.User;

@Service
public interface UserService {

	User insert(User user);

	List<User> findAll();

	User findById(Long userId);

	User update(Long userId, User user);

	void deleteById(Long userId);
}
