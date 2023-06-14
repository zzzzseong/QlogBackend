package com.Qlog.backend.controller;

import com.Qlog.backend.consts.SessionConst;
import com.Qlog.backend.controller.dto.comment.CommentCreateRequest;
import com.Qlog.backend.controller.dto.comment.CommentUpdateRequest;
import com.Qlog.backend.domain.Comment;
import com.Qlog.backend.domain.QCard;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.CommentService;
import com.Qlog.backend.service.QCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final QCardService qCardService;

    @PostMapping("/create")
    public void createComment(@RequestBody CommentCreateRequest request,
                              @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return;

        QCard findQCard = qCardService.findById(request.getQCardId());
        Comment comment = new Comment(user, findQCard, request.getComment());

        findQCard.getComments().add(comment);
        user.getComments().add(comment);

        commentService.save(comment);
    }

    @PutMapping("/update/{commentId}")
    public void updateComment(@PathVariable Long commentId,
                              @RequestBody CommentUpdateRequest request,
                              @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return;

        Comment findComment = commentService.findById(commentId);
        if(findComment == null) return;

        findComment.update(request.getComment());
    }

    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return;

        Comment findComment = commentService.findById(commentId);
        if(findComment == null) return;

        commentService.delete(findComment);
    }

}
