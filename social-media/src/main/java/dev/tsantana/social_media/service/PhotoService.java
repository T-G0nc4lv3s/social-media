package dev.tsantana.social_media.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.tsantana.social_media.DTO.InsertPhotoDTO;
import dev.tsantana.social_media.domain.Photo;

public interface PhotoService {

	Photo insert(InsertPhotoDTO photoDTO);

	Page<Photo> findAllPaged(Pageable pageable);

	Photo findById(Long photoId);

	Photo update(Long photoId, InsertPhotoDTO photoDTO);

	void deleteById(Long photoId);
}
