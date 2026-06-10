package com.example.parentchilddbrelation.controller;

import com.example.parentchilddbrelation.dto.Child1Dto;
import com.example.parentchilddbrelation.service.Child1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/child1s")
@RequiredArgsConstructor
public class Child1Controller {

    private final Child1Service child1Service;

    @GetMapping
    public ResponseEntity<List<Child1Dto>> findAll() {
        return ResponseEntity.ok(child1Service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Child1Dto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(child1Service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Child1Dto> create(@RequestBody Child1Dto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(child1Service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Child1Dto> update(@PathVariable Long id, @RequestBody Child1Dto dto) {
        return ResponseEntity.ok(child1Service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        child1Service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
