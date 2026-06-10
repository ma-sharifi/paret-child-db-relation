package com.example.parentchilddbrelation.mapper;

import com.example.parentchilddbrelation.dto.Child1Dto;
import com.example.parentchilddbrelation.entity.Child1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface Child1Mapper extends BaseMapper<Child1, Child1Dto> {

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(Child1Dto dto, @MappingTarget Child1 entity);
}
