package com.Qlog.backend.controller;

import com.Qlog.backend.consts.SessionConst;
import com.Qlog.backend.controller.dto.qCard.QCardCommentsResponse;
import com.Qlog.backend.controller.dto.qCard.QCardCreateRequest;
import com.Qlog.backend.controller.dto.qCard.QCardResponse;
import com.Qlog.backend.domain.Comment;
import com.Qlog.backend.domain.QCard;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.QCardService;
import com.Qlog.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qCard")
public class QCardController {

    private final QCardService qCardService;
    private final UserService userService;

    @PostMapping("/create")
    public void createQCard(@RequestBody QCardCreateRequest request,
                            @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return;
        User findUser = userService.findById(user.getId());

        System.out.println(request.getQuestion());

        QCard qCard = new QCard(findUser, request.getQuestion());
        findUser.getQCards().add(qCard);
        findUser.updatePoint(10);

        qCardService.save(qCard);
    }

    @GetMapping("/read/{qCardId}")
    public QCardResponse readQCard(@PathVariable Long qCardId,
                                   @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;

        QCard findQCard = qCardService.findById(qCardId);
        return new QCardResponse(findQCard.getQuestion(), findQCard.getComments());
    }

    @GetMapping("/readComments/{qCardId}")
    public List<QCardCommentsResponse> readComments(@PathVariable Long qCardId,
                                                    @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;

        QCard findQCard = qCardService.findById(qCardId);
        List<Comment> comments = findQCard.getComments();

        List<QCardCommentsResponse> res = new ArrayList<>();
        for (Comment comment : comments) {
            String imgPath = "https://qlogbucket.s3.ap-northeast-2.amazonaws.com/user_profile/"
                    + comment.getComment_user().getProfileImageName();

            res.add(new QCardCommentsResponse(imgPath, comment.getComment_user().getName(), comment.getComment()));
        }

        return res;
    }

    @PutMapping("/update/{qCardId}")
    public void updateQCard(@RequestBody QCardCreateRequest request,
                            @PathVariable Long qCardId,
                            @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return;

        QCard findQCard = qCardService.findById(qCardId);
        findQCard.updateQuestion(request.getQuestion());
    }


    @DeleteMapping("/delete/{qCardId}")
    public void deleteQCard(@PathVariable Long qCardId,
                            @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return;

        User findUser = userService.findById(user.getId());
        QCard findQCard = qCardService.findById(qCardId);
        findUser.getQCards().remove(findQCard);
        findUser.updatePoint(-10);

        qCardService.delete(findQCard);
    }
}