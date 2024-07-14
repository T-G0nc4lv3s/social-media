package dev.tsantana.social_media.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.tsantana.social_media.DTO.InsertAlbumDTO;
import dev.tsantana.social_media.domain.Album;
import dev.tsantana.social_media.service.AlbumService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/albuns")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@GetMapping(value = "/{albumId}")
	public ResponseEntity<Album> findById(@PathVariable Long albumId) {
		Album response = albumService.findById(albumId);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Page<Album>> findAll(Pageable pageable) {
		Page<Album> response = albumService.findAllPaged(pageable);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Album> insert(@RequestBody @Valid InsertAlbumDTO albumDTO) {
		Album response = albumService.insert(albumDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/albumId}").buildAndExpand(response.getId())
				.toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@PutMapping(value = "/{albumId}")
	public ResponseEntity<Album> update(@PathVariable Long albumId, @RequestBody InsertAlbumDTO albumDTO) {
		Album response = albumService.update(albumId, albumDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{albumId}")
	public ResponseEntity<Void> delete(@PathVariable Long albumId) {
		albumService.deleteById(albumId);
		return ResponseEntity.noContent().build();
	}
}
