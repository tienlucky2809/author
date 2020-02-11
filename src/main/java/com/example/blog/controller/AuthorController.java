package com.example.blog.controller;

import com.example.blog.entity.Author;

import com.example.blog.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apiAuthor")
public class AuthorController {

    private final AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody Author author) {
        author.setId(UUID.randomUUID().toString());
        Author newAuthor = authorRepository.save(author);
        return ResponseEntity.ok(newAuthor);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Author> categories = authorRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(author.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody Author author) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author existingAuthor = optionalAuthor.get();
            existingAuthor.setFistName(author.getFistName());
            existingAuthor.setLastName(author.getLastName());
            Author savedAuthor = authorRepository.save(existingAuthor);
            return ResponseEntity.ok(savedAuthor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            authorRepository.delete(optionalAuthor.get());
            return ResponseEntity.ok("Delete successful.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
