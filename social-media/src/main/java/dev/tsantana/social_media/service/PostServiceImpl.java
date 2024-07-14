package dev.tsantana.social_media.service;

import java.time.Instant;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dev.tsantana.social_media.DTO.InsertPostDTO;
import dev.tsantana.social_media.domain.Post;
import dev.tsantana.social_media.domain.User;
import dev.tsantana.social_media.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public Post insert(InsertPostDTO postDTO) {
		Post post = new Post();
		try {
			User user = userService.findById(postDTO.getUserId());
			BeanUtils.copyProperties(postDTO, post);
			post.setUser(user);
			post = postRepository.save(post);
			return post;
		}catch (Exception e) {
			System.out.println(e.getClass());
			throw new EntityNotFoundException("User not found, userId: "+ postDTO.getUserId());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Post> findAllPaged(Pageable pageable) {
		Page<Post> result = postRepository.findAll(pageable);
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public Post findById(Long postId) {
		Post entity = postRepository.findById(postId)
				.orElseThrow(() -> new EntityNotFoundException("Entity not found."));
		return entity;
	}

	@Transactional
	@Override
	public Post update(Long postId, InsertPostDTO postDTO) {
		try {
			Post entity = postRepository.getReferenceById(postId);
			BeanUtils.copyProperties(postDTO, entity);
			entity.setUpdated(Instant.now());
			entity = postRepository.save(entity);
			return entity;
		} catch (Exception e) {
			System.out.println(e.getClass());
			throw new EntityNotFoundException("Entity not found: " + postId);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteById(Long postId) {
		try {
			postRepository.deleteById(postId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Id not found: " + postId);
		}

	}

}
