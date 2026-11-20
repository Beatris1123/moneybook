package com.example.moneybook.controller;

import com.example.moneybook.dto.EntryRequest;
import com.example.moneybook.entity.Entry;
import com.example.moneybook.service.EntryService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }
    @GetMapping("/api/entry")
    public List<Entry> show(){
        return entryService.show();
    }

    @PostMapping("/api/entry")
    public Entry create(@Valid @RequestBody EntryRequest request) {
        return entryService.create(request);
    }

    @GetMapping("/api/entry/{id}")
    public Entry findById(@PathVariable Long id){
        return entryService.findById(id);
    }

    @PutMapping("/api/entry/{id}")
    public Entry update(@PathVariable Long id, @Valid @RequestBody EntryRequest request){
        return entryService.update(id, request);
    }
    @DeleteMapping("/api/entry/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        entryService.delete(id);

    }
}
