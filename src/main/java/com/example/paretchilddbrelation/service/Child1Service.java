package com.example.paretchilddbrelation.service;

import com.example.paretchilddbrelation.dto.Child1Dto;
import com.example.paretchilddbrelation.entity.Child1;
import com.example.paretchilddbrelation.exception.EntityNotFoundException;
import com.example.paretchilddbrelation.mapper.Child1Mapper;
import com.example.paretchilddbrelation.repository.Child1Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Child1Service {

    private final Child1Repository child1Repository;
    private final Child1Mapper child1Mapper;

    public List<Child1Dto> findAll() {
        return child1Mapper.toDtoList(child1Repository.findAll());
    }

    public Child1Dto findById(Long id) {
        Child1 child1 = child1Repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Child1", id));
        return child1Mapper.toDto(child1);
    }

    @Transactional
    public Child1Dto create(Child1Dto dto) {
        Child1 child1 = child1Mapper.toEntity(dto);
        return child1Mapper.toDto(child1Repository.save(child1));
    }

    @Transactional
    public Child1Dto update(Long id, Child1Dto dto) {
        Child1 child1 = child1Repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Child1", id));
        child1Mapper.updateEntityFromDto(dto, child1);
        return child1Mapper.toDto(child1Repository.save(child1));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!child1Repository.existsById(id)) {
            throw new EntityNotFoundException("Child1", id);
        }
        child1Repository.deleteById(id);
    }
}
