package com.example.paretchilddbrelation.service;

import com.example.paretchilddbrelation.dto.Child2Dto;
import com.example.paretchilddbrelation.entity.Child2;
import com.example.paretchilddbrelation.exception.EntityNotFoundException;
import com.example.paretchilddbrelation.mapper.Child2Mapper;
import com.example.paretchilddbrelation.repository.Child2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Child2Service {

    private final Child2Repository child2Repository;
    private final Child2Mapper child2Mapper;

    public List<Child2Dto> findAll() {
        return child2Mapper.toDtoList(child2Repository.findAll());
    }

    public Child2Dto findById(Long id) {
        Child2 child2 = child2Repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Child2", id));
        return child2Mapper.toDto(child2);
    }

    @Transactional
    public Child2Dto create(Child2Dto dto) {
        Child2 child2 = child2Mapper.toEntity(dto);
        return child2Mapper.toDto(child2Repository.save(child2));
    }

    @Transactional
    public Child2Dto update(Long id, Child2Dto dto) {
        Child2 child2 = child2Repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Child2", id));
        child2Mapper.updateEntityFromDto(dto, child2);
        return child2Mapper.toDto(child2Repository.save(child2));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!child2Repository.existsById(id)) {
            throw new EntityNotFoundException("Child2", id);
        }
        child2Repository.deleteById(id);
    }
}
