package com.Qlog.backend.repository;

import com.Qlog.backend.domain.QCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QCardRepository extends JpaRepository<QCard, Long> {

    @Query("select q from QCard q where q.qCard_user.name <> :name and q.solved <> true order by rand() limit 1")
    public QCard findRandomQCard(@Param("name") String name);
}