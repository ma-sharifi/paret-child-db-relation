package com.example.paretchilddbrelation.mapper;

import com.example.paretchilddbrelation.dto.ParentDto;
import com.example.paretchilddbrelation.entity.Parent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParentMapper extends BaseMapper<Parent, ParentDto> {
}
