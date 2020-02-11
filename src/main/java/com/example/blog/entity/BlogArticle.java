package com.example.blog.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "BlogArticle")
@NoArgsConstructor

public class BlogArticle {
    @Id
    @Getter @Setter private String id;
    @Getter @Setter private String name;
    @Getter @Setter @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @Getter @Setter @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Getter @Setter private LocalDateTime creatDate;
    @Getter private LocalDateTime modifiedDate;


    public BlogArticle(String id, String name, Author author, Category category)
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.creatDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }
    public void setModifiedDate()
    {
        this.modifiedDate = LocalDateTime.now();
    }

}
