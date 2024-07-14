package dev.tsantana.social_media.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dev.tsantana.social_media.DTO.InsertPhotoDTO;
import dev.tsantana.social_media.domain.Album;
import dev.tsantana.social_media.domain.Photo;
import dev.tsantana.social_media.domain.Post;
import dev.tsantana.social_media.domain.User;
import dev.tsantana.social_media.repository.AlbumRepository;
import dev.tsantana.social_media.repository.PhotoRepository;
import dev.tsantana.social_media.repository.PostRepository;
import dev.tsantana.social_media.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private PhotoRepository photoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Override
	public Photo update(Long photoId, InsertPhotoDTO photoDTO) {
		Photo photo = photoRepository.findById(photoId)
				.orElseThrow(() -> new EntityNotFoundException("Photo not found: " + photoId));

		BeanUtils.copyProperties(photoDTO, photo);

		Post post = postRepository.findById(photoDTO.getPostId()).orElse(null);
		photo.setPost(post);

		Album album = albumRepository.findById(photoDTO.getAlbumId()).orElse(null);
		photo.setAlbum(album);

		photo = photoRepository.save(photo);
		return photo;
	}

	@Transactional
	@Override
	public Photo insert(InsertPhotoDTO photoDTO) {
		Photo photo = new Photo();

		Post post = postRepository.findById(photoDTO.getPostId()).orElse(null);
		photo.setPost(post);

		Album album = albumRepository.findById(photoDTO.getAlbumId()).orElse(null);
		photo.setAlbum(album);

		photo.setUri(photoDTO.getUri());

		User user = userRepository.findById(photoDTO.getUserId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		photo.setUser(user);
		photo = photoRepository.save(photo);
		return photo;

	}

	@Transactional(readOnly = true)
	@Override
	public Page<Photo> findAllPaged(Pageable pageable) {
		Page<Photo> result = photoRepository.findAll(pageable);
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public Photo findById(Long photoId) {
		Photo entity = photoRepository.findById(photoId)
				.orElseThrow(() -> new EntityNotFoundException("Entity not found: " + photoId));
		return entity;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteById(Long photoId) {
		try {
			photoRepository.deleteById(photoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Id not found: " + photoId);
		}
	}

}
