package com.example.parentchilddbrelation.mapper;

import com.example.parentchilddbrelation.dto.Child1Dto;
import com.example.parentchilddbrelation.entity.Child1;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Child1Mapper extends BaseMapper<Child1, Child1Dto> {
}
