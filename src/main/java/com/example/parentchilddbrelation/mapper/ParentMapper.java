package com.example.parentchilddbrelation.mapper;

import com.example.parentchilddbrelation.dto.ParentDto;
import com.example.parentchilddbrelation.entity.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParentMapper extends BaseMapper<Parent, ParentDto> {

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ParentDto dto, @MappingTarget Parent entity);
}
