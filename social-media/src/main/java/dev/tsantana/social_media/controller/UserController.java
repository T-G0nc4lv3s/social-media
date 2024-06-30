package dev.tsantana.social_media.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.tsantana.social_media.domain.User;
import dev.tsantana.social_media.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> response = userService.findAllPaged(pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<User> findById(@PathVariable Long userId) {
		User response = userService.findById(userId);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<User> insert(@RequestBody @Valid User user) {
		User response = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/userId}").buildAndExpand(response.getId())
				.toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@PutMapping(value = "/{userId}")
	public ResponseEntity<User> update(@PathVariable Long userId, @RequestBody User user) {
		User response = userService.update(userId, user);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<Void> delete(@PathVariable Long userId) {
		userService.deleteById(userId);
		return ResponseEntity.noContent().build();
	}
}
