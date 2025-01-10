package com.blogapp.blog.articles;

import org.springframework.stereotype.Service;

@Service
public class ArticlesService {
    private final ArticleRepository articleRepository;

    public ArticlesService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
