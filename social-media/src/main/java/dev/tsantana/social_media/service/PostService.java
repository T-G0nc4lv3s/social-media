package dev.tsantana.social_media.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.tsantana.social_media.DTO.InsertPostDTO;
import dev.tsantana.social_media.domain.Post;

@Service
public interface PostService {

	Post insert(InsertPostDTO postDTO);

	Page<Post> findAllPaged(Pageable pageable);

	Post findById(Long postId);

	Post update(Long postId, InsertPostDTO postDTO);

	void deleteById(Long postId);
}
