package com.example.parentchilddbrelation.mapper;

import com.example.parentchilddbrelation.dto.Child2Dto;
import com.example.parentchilddbrelation.entity.Child2;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Child2Mapper extends BaseMapper<Child2, Child2Dto> {
}
