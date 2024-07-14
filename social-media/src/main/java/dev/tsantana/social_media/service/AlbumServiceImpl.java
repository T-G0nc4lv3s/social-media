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

import dev.tsantana.social_media.DTO.InsertAlbumDTO;
import dev.tsantana.social_media.domain.Album;
import dev.tsantana.social_media.domain.User;
import dev.tsantana.social_media.repository.AlbumRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public Album insert(InsertAlbumDTO albumDTO) {
		Album album = new Album();
		try {
			User user = userService.findById(albumDTO.getUserId());
			BeanUtils.copyProperties(albumDTO, album);
			album.setUser(user);
			album = albumRepository.save(album);
			return album;
		} catch (Exception e) {
			System.out.println(e.getClass());
			throw new EntityNotFoundException("User not found, userId: " + albumDTO.getUserId());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Album> findAllPaged(Pageable pageable) {
		Page<Album> result = albumRepository.findAll(pageable);
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public Album findById(Long albumId) {
		Album entity = albumRepository.findById(albumId)
				.orElseThrow(() -> new EntityNotFoundException("Entity not found."));
		return entity;
	}

	@Transactional
	@Override
	public Album update(Long albumId, InsertAlbumDTO albumDTO) {
		try {
			Album entity = albumRepository.getReferenceById(albumId);
			BeanUtils.copyProperties(albumDTO, entity);
			entity.setUpdated(Instant.now());
			entity = albumRepository.save(entity);
			return entity;
		} catch (Exception e) {
			System.out.println(e.getClass());
			throw new EntityNotFoundException("Entity not found: " + albumId);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void deleteById(Long postId) {
		try {
			albumRepository.deleteById(postId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Id not found: " + postId);
		}

	}

}
