package dev.tsantana.social_media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.tsantana.social_media.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
