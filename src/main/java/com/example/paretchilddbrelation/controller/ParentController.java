package com.example.paretchilddbrelation.controller;

import com.example.paretchilddbrelation.dto.ParentDto;
import com.example.paretchilddbrelation.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @GetMapping
    public ResponseEntity<List<ParentDto>> findAll() {
        return ResponseEntity.ok(parentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(parentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParentDto> create(@RequestBody ParentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parentService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentDto> update(@PathVariable Long id, @RequestBody ParentDto dto) {
        return ResponseEntity.ok(parentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
