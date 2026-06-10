package com.example.parentchilddbrelation.mapper;

import com.example.parentchilddbrelation.dto.ParentDto;
import com.example.parentchilddbrelation.entity.Parent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParentMapper extends BaseMapper<Parent, ParentDto> {
}
