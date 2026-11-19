package com.example.moneybook.repository;

import com.example.moneybook.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);

}
