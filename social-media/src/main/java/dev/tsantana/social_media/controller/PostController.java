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

import dev.tsantana.social_media.DTO.InsertPostDTO;
import dev.tsantana.social_media.domain.Post;
import dev.tsantana.social_media.service.PostService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping(value = "/{postId}")
	public ResponseEntity<Post> findById(@PathVariable Long postId) {
		Post response = postService.findById(postId);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Page<Post>> findAll(Pageable pageable) {
		Page<Post> response = postService.findAllPaged(pageable);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Post> insert(@RequestBody @Valid InsertPostDTO postDTO) {
		Post response = postService.insert(postDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/postId}").buildAndExpand(response.getId())
				.toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@PutMapping(value = "/{postId}")
	public ResponseEntity<Post> update(@PathVariable Long postId, @RequestBody InsertPostDTO postDTO) {
		Post response = postService.update(postId, postDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{postId}")
	public ResponseEntity<Void> delete(@PathVariable Long postId) {
		postService.deleteById(postId);
		return ResponseEntity.noContent().build();
	}
}
