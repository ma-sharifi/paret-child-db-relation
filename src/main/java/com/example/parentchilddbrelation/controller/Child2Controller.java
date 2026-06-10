package com.example.parentchilddbrelation.controller;

import com.example.parentchilddbrelation.dto.Child2Dto;
import com.example.parentchilddbrelation.service.Child2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/child2s")
@RequiredArgsConstructor
public class Child2Controller {

    private final Child2Service child2Service;

    @GetMapping
    public ResponseEntity<List<Child2Dto>> findAll() {
        return ResponseEntity.ok(child2Service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Child2Dto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(child2Service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Child2Dto> create(@RequestBody Child2Dto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(child2Service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Child2Dto> update(@PathVariable Long id, @RequestBody Child2Dto dto) {
        return ResponseEntity.ok(child2Service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        child2Service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
