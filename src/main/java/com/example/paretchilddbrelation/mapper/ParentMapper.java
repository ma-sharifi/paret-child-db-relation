package com.example.paretchilddbrelation.mapper;

import com.example.paretchilddbrelation.dto.ParentDto;
import com.example.paretchilddbrelation.entity.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    ParentDto toDto(Parent parent);

    Parent toEntity(ParentDto dto);

    List<ParentDto> toDtoList(List<Parent> parents);

    void updateEntityFromDto(ParentDto dto, @MappingTarget Parent parent);
}
