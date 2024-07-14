package dev.tsantana.social_media.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User {

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private LocalDate birth;
	private String gender;
	private String photoUri;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private Set<User> followers = new HashSet<User>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private Set<User> followed = new HashSet<User>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Post> posts = new HashSet<Post>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Album> albums = new HashSet<Album>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Photo> photos = new ArrayList<Photo>();

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
		user.removeFollower(this);
		this.followed.remove(user);
	}

	public void addPhotos(Photo photo) {
		this.photos.add(photo);
	}

	public void removePhotos(Photo photo) {
		this.photos.remove(photo);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", birth=" + birth + ", gender=" + gender
				+ ", photoUri=" + photoUri + ", posts=" + posts + "]";
	}

}
