package dev.tsantana.social_media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.tsantana.social_media.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
