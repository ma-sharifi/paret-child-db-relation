package com.example.parentchilddbrelation.mapper;

import com.example.parentchilddbrelation.dto.Child2Dto;
import com.example.parentchilddbrelation.entity.Child2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface Child2Mapper extends BaseMapper<Child2, Child2Dto> {

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(Child2Dto dto, @MappingTarget Child2 entity);
}
