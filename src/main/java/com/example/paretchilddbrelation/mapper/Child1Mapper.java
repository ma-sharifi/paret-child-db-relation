package com.example.paretchilddbrelation.mapper;

import com.example.paretchilddbrelation.dto.Child1Dto;
import com.example.paretchilddbrelation.entity.Child1;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Child1Mapper {

    Child1Dto toDto(Child1 child1);

    Child1 toEntity(Child1Dto dto);

    List<Child1Dto> toDtoList(List<Child1> child1List);

    void updateEntityFromDto(Child1Dto dto, @MappingTarget Child1 child1);
}
