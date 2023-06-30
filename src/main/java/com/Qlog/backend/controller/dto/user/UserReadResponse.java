package com.Qlog.backend.controller.dto.user;

import com.Qlog.backend.domain.QCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserReadResponse {
    private String name;
    private String introduction;
    private int point;
    private String tier;
    private String imgPath;

    private List<QCardRead> qCards = new ArrayList<>();

    public UserReadResponse(String name, String introduction, int point, String tier, String imgPath, List<QCard> qCards) {
        this.name = name;
        this.introduction = introduction;
        this.point = point;
        this.tier = tier;
        this.imgPath = imgPath;
        for (QCard qCard : qCards) {
            this.qCards.add(new QCardRead(qCard.getId(), name, qCard.getQuestion(), qCard.isSolved()));
        }
    }
}

@Data
@AllArgsConstructor
class QCardRead {
    private Long id;
    private String name;
    private String question;
    private boolean solved;
}
