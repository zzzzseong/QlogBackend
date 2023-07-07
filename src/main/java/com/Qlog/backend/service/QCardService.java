package com.Qlog.backend.service;

import com.Qlog.backend.domain.Comment;
import com.Qlog.backend.domain.QCard;
import com.Qlog.backend.repository.QCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QCardService {

    private final QCardRepository qCardRepository;

    @Transactional
    public void save(QCard qCard) {
        qCardRepository.save(qCard);
    }

    @Transactional
    public void delete(QCard qCard) {
        qCardRepository.delete(qCard);
    }

    @Transactional
    public void updatedSolved(QCard qCard, boolean solved) {
        qCard.setSolved(solved);
    }

    public QCard findById(Long id) {
        return qCardRepository.findById(id).orElse(null);
    }

    public List<QCard> findAll() {
        return qCardRepository.findAll();
    }
}
