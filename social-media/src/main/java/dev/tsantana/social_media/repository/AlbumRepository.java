package dev.tsantana.social_media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.tsantana.social_media.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
