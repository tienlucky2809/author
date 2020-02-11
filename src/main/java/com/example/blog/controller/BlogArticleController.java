package com.example.blog.controller;

import com.example.blog.entity.BlogArticle;

import com.example.blog.repository.BlogArticleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apiBlogArticle")
public class BlogArticleController {
    private final BlogArticleRepository blogArticleRepository;

    @GetMapping
    public ResponseEntity getAll() {
        List<BlogArticle> arrBlog = blogArticleRepository.findAll();
        for(int i=0; i<arrBlog.size()-1; i++)
        {
            for(int j=0; j<arrBlog.size()-i-1; j++)
            {
                BlogArticle tmp1 = arrBlog.get(j);
                BlogArticle tmp2 = arrBlog.get(j+1);
                if(tmp2.getModifiedDate().isBefore(tmp1.getModifiedDate())==true) Collections.swap(arrBlog,j,j+1);
            }
        }
        return ResponseEntity.ok(arrBlog);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        Optional<BlogArticle> blogArticle = blogArticleRepository.findById(id);
        if (blogArticle.isPresent()) {
            return ResponseEntity.ok(blogArticle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BlogArticle blogArticle) {
        blogArticle.setId(UUID.randomUUID().toString());
        blogArticle.setCreatDate(LocalDateTime.now());
        blogArticle.setModifiedDate();
        BlogArticle newBlogArticle1 = blogArticleRepository.save(blogArticle);
        return ResponseEntity.ok(newBlogArticle1);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody BlogArticle blogArticle) {
        Optional<BlogArticle> optionalBlogArticle = blogArticleRepository.findById(id);
        if (optionalBlogArticle.isPresent()) {
            BlogArticle existingBlogArticle = optionalBlogArticle.get();
            existingBlogArticle.setName(existingBlogArticle.getName());
            existingBlogArticle.setModifiedDate();
            BlogArticle saveBlogArticle = blogArticleRepository.save(existingBlogArticle);
            return ResponseEntity.ok(saveBlogArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<BlogArticle> optionalBlogArticle = blogArticleRepository.findById(id);
        if (optionalBlogArticle.isPresent()) {
            blogArticleRepository.delete(optionalBlogArticle.get());
            return ResponseEntity.ok("Delete successful.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
