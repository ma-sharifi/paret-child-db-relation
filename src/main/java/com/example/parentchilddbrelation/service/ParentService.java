package com.example.parentchilddbrelation.service;

import com.example.parentchilddbrelation.dto.ParentDto;
import com.example.parentchilddbrelation.entity.Parent;
import com.example.parentchilddbrelation.exception.EntityNotFoundException;
import com.example.parentchilddbrelation.mapper.ParentMapper;
import com.example.parentchilddbrelation.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParentService {

    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;

    public List<ParentDto> findAll() {
        return parentMapper.toDtoList(parentRepository.findAll());
    }

    public ParentDto findById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent", id));
        return parentMapper.toDto(parent);
    }

    @Transactional
    public ParentDto create(ParentDto dto) {
        Parent parent = parentMapper.toEntity(dto);
        return parentMapper.toDto(parentRepository.save(parent));
    }

    @Transactional
    public ParentDto update(Long id, ParentDto dto) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent", id));
        parentMapper.updateEntityFromDto(dto, parent);
        return parentMapper.toDto(parentRepository.save(parent));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent", id);
        }
        parentRepository.deleteById(id);
    }
}
