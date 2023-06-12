package com.Qlog.backend.repository;

import com.Qlog.backend.domain.QCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QCardRepository extends JpaRepository<QCard, Long> {
}
