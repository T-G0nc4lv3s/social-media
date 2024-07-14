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

import dev.tsantana.social_media.DTO.InsertPhotoDTO;
import dev.tsantana.social_media.domain.Photo;
import dev.tsantana.social_media.service.PhotoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/photos")
public class PhotoController {

	@Autowired
	private PhotoService photoService;

	@GetMapping(value = "/{photoId}")
	public ResponseEntity<Photo> findById(@PathVariable Long photoId) {
		Photo response = photoService.findById(photoId);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Page<Photo>> findAll(Pageable pageable) {
		Page<Photo> response = photoService.findAllPaged(pageable);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Photo> insert(@RequestBody @Valid InsertPhotoDTO photoDTO) {
		Photo response = photoService.insert(photoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/photoId}").buildAndExpand(response.getId())
				.toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@PutMapping(value = "/{photoId}")
	public ResponseEntity<Photo> update(@PathVariable Long photoId, @RequestBody InsertPhotoDTO postDTO) {
		Photo response = photoService.update(photoId, postDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{photoId}")
	public ResponseEntity<Void> delete(@PathVariable Long photoId) {
		photoService.deleteById(photoId);
		return ResponseEntity.noContent().build();
	}
}
