package com.blogapp.blog.articles;

import com.blogapp.blog.articles.dtos.CreateArticleRequest;
import com.blogapp.blog.users.UserRepository;
import com.blogapp.blog.users.UsersService;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticlesService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }


    public Iterable<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article =  articleRepository.findBySlug(slug);

        if(article == null) {
            throw new ArticleNotFoundException(slug);
        }

        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest article, Long authorId) {
        var author = userRepository.findById(authorId).orElseThrow(() -> new UsersService.UserNotFoundException(authorId));
        return articleRepository.save(ArticleEntity.builder()
                .title(article.getTitle())
                .slug(article.getTitle().toLowerCase().replaceAll("\\s", "-")) // TODO: Slugify
                .body(article.getBody())
                .subtitle(article.getSubtitle())
                .author(author)
                .build());
    }

    public ArticleEntity updateArticle(Long articleId, CreateArticleRequest articleRequest) {
        var article = articleRepository.getReferenceById(articleId);
        if(article == null) {
            throw new ArticleNotFoundException(articleId);
        }

        article.setTitle(articleRequest.getTitle() != null? articleRequest.getTitle(): article.getTitle());
        article.setSlug(articleRequest.getTitle().toLowerCase().replaceAll("\\s" , "-"));
        article.setBody(articleRequest.getBody() != null? articleRequest.getBody(): article.getBody());
        article.setSubtitle(articleRequest.getSubtitle() != null? articleRequest.getSubtitle(): article.getSubtitle());

        return articleRepository.save(article);
    }

    class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article " + slug + " not found!!!");
        }

        public ArticleNotFoundException(Long articleId) {
            super("Article with id: " + articleId + " not found!!!");
        }
    }
}
