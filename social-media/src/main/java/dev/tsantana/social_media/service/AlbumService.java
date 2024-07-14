package dev.tsantana.social_media.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.tsantana.social_media.DTO.InsertAlbumDTO;
import dev.tsantana.social_media.domain.Album;

@Service
public interface AlbumService {

	Album insert(InsertAlbumDTO albumDTO);

	Page<Album> findAllPaged(Pageable pageable);

	Album findById(Long albumId);

	Album update(Long albumId, InsertAlbumDTO albumDTO);

	void deleteById(Long albumId);
}
