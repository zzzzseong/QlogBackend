package com.Qlog.backend.controller.dto.user;

import com.Qlog.backend.domain.Comment;
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
    private int point;
    private String tier;

    private List<QCardRead> qCards = new ArrayList<>();

    public UserReadResponse(String name, int point, String tier, List<QCard> qCards) {
        this.name = name;
        this.point = point;
        this.tier = tier;
        for (QCard qCard : qCards) {
            this.qCards.add(new QCardRead(qCard.getId(), qCard.getQuestion(), qCard.isSolved()));
        }
    }
}

@Data
@AllArgsConstructor
class QCardRead {
    private Long id;
    private String question;
    private boolean solved;
}
