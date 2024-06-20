package dev.tsantana.social_media.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@NoArgsConstructor
public class User {

	@Include
	private Long id;
	private String name;
	private String email;
	private LocalDate birth;
	private String gender;
	private String photoUri;
	private Set<User> followers = new HashSet<User>();
	private Set<User> followed = new HashSet<User>();
	private Set<Post> posts = new HashSet<Post>();
	private Set<Album> albums = new HashSet<Album>();

	public User(Long id, String name, String email, LocalDate birth, String gender, String photoUri) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
		this.photoUri = photoUri;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}

	private void addFollower(User user) {
		this.followers.add(user);
	}

	public void removeFollower(User user) {
		this.followers.remove(user);
	}

	public void addFollowed(User user) {
		this.followed.add(user);
		user.addFollower(this);
	}

	public void removeFollowed(User user) {
		this.followed.remove(user);
	}

	public void addPost(Post post) {
		this.posts.add(post);
	}

	public void removePost(Post post) {
		this.posts.remove(post);
	}

	public void addAlbum(Album album) {
		this.albums.add(album);
	}

	public void removeAlbum(Album album) {
		this.albums.remove(album);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", birth=" + birth + ", gender=" + gender
				+ ", photoUri=" + photoUri + ", posts=" + posts + ", albums=" + albums + "]";
	}

}
