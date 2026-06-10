package com.example.paretchilddbrelation.mapper;

import com.example.paretchilddbrelation.dto.Child2Dto;
import com.example.paretchilddbrelation.entity.Child2;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Child2Mapper {

    Child2Dto toDto(Child2 child2);

    Child2 toEntity(Child2Dto dto);

    List<Child2Dto> toDtoList(List<Child2> child2List);

    void updateEntityFromDto(Child2Dto dto, @MappingTarget Child2 child2);
}
