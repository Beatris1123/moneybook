package com.example.moneybook.service;

import com.example.moneybook.dto.EntryRequest;
import com.example.moneybook.entity.Entry;
import com.example.moneybook.repository.EntryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EntryService {
    private final EntryRepository entryRepository;


    public List<Entry> show() {
        return entryRepository.findAll();
    }

    public Entry create(EntryRequest request) {
        Entry entry = new Entry(request.type(), request.amount(), request.category(), request.memo(), request.date());
        return entryRepository.save(entry);
    }

    @Transactional
    public Entry findById(Long id) {
        return entryRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "내역이 없습니다. Id" + id));
    }

    @Transactional
    public Entry update(Long id, EntryRequest request) {
        Entry entry = findById(id);
        entry.update(request.type(), request.amount(), request.category(),
                request.memo(), request.date());
        return entry;
    }

    @Transactional
    public void delete(Long id) {
        Entry entry = findById(id);
        entryRepository.delete(entry);
    }
}
