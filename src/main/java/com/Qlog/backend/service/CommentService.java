package com.Qlog.backend.service;

import com.Qlog.backend.domain.Comment;
import com.Qlog.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
