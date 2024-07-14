package dev.tsantana.social_media.tests;

import java.time.LocalDate;

import dev.tsantana.social_media.DTO.InsertAlbumDTO;
import dev.tsantana.social_media.DTO.InsertPhotoDTO;
import dev.tsantana.social_media.DTO.InsertPostDTO;
import dev.tsantana.social_media.domain.Album;
import dev.tsantana.social_media.domain.Photo;
import dev.tsantana.social_media.domain.Post;
import dev.tsantana.social_media.domain.User;

public class Factory {

	public static User createUser() {
		User user = new User(null, "Bob", "bob@gmail.com", LocalDate.of(1998, 05, 11), "male", null);
		return user;
	}

	public static Post createPost() {
		User user = createUser();
		Post post = new Post(null, "Lorem ipsum dolor sit amet.", user);
		return post;
	}

	public static Album createAlbum() {
		User user = createUser();
		Album album = new Album(null, "Férias de 2024", user);
		return album;
	}

	public static Photo createPhoto() {
		User user = createUser();
		user.setId(1L);
		Photo photo = new Photo(null, "URI", null, null, user);
		return photo;
	}

	public static InsertPostDTO createPostDTO() {
		Post post = createPost();
		InsertPostDTO dto = new InsertPostDTO(post);
		return dto;
	}

	public static InsertAlbumDTO createAlbumDTO() {
		Album album = createAlbum();
		InsertAlbumDTO dto = new InsertAlbumDTO(album);
		return dto;
	}

	public static InsertPhotoDTO createPhotoDTO() {
		Photo photo = createPhoto();
		InsertPhotoDTO dto = new InsertPhotoDTO(photo);
		return dto;
	}
}
