package com.blogapp.blog.coments;

import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    private final CommentRepository commentRepository;

    public CommentsService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
