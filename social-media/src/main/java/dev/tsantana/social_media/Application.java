package dev.tsantana.social_media;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.tsantana.social_media.domain.Album;
import dev.tsantana.social_media.domain.Photo;
import dev.tsantana.social_media.domain.Post;
import dev.tsantana.social_media.domain.User;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		// Instanciando usuários
		User user = new User(1L, "Bob", "bob@gmail.com", null, "male",
				"https://upload.wikimedia.org/wikipedia/commons/7/7c/Avatar.png");

		User user2 = new User(2L, "Ana", "ana@gmail.com", null, "female", null);

		User user3 = new User(3L, "Hioni", null, null, null, null);

		User user4 = new User(4L, "Sieusguk", null, null, null, null);

		// Seguindo
		user2.addFollowed(user);
		user3.addFollowed(user);
		user4.addFollowed(user);

		user.addFollowed(user2);

		System.out.println(user);
		System.out.println(user2);

		// Listas de seguidores e seguidos
		System.out.println("Seguidos de " + user.getName());
		System.out.println(user.getFollowed());
		System.out.println("Seguidores de " + user.getName());
		System.out.println(user.getFollowers());

		System.out.println("----------");

		System.out.println("Seguidos de " + user2.getName());
		System.out.println(user2.getFollowed());
		System.out.println("Seguidores de " + user2.getName());
		System.out.println(user2.getFollowers());

		System.out.println("----------");
		// Instanciando post

		Post post1 = new Post(1L, "Primeiro post", user);

		Photo photo1 = new Photo(
				"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXIruZA5wwlDw0Rk5HomsNfByR3G9BN3NKZ9W6ppkC1FxP5IdmFz3c4d9iV6xcxIq6m7A&usqp=CAU",
				null, post1, user);

		photo1.setPost(post1);

		// Instanciando álbum

		Album album1 = new Album(1L, "Férias 2024", user);

		photo1.setAlbum(album1);
		System.out.println(post1);
		System.out.println(album1);

		System.out.println("----------");

		System.out.println(user);
	}

}
