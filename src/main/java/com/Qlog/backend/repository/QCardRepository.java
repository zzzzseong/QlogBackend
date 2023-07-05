package com.Qlog.backend.repository;

import com.Qlog.backend.domain.QCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QCardRepository extends JpaRepository<QCard, Long> {

}
