package com.blogapp.blog.coments;

import com.blogapp.blog.articles.ArticleEntity;
import com.blogapp.blog.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nullable
    private String title;

    @NonNull
    private String body;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "articleId", nullable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private UserEntity author;
}
