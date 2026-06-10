package com.example.paretchilddbrelation.mapper;

import com.example.paretchilddbrelation.dto.Child1Dto;
import com.example.paretchilddbrelation.entity.Child1;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Child1Mapper extends BaseMapper<Child1, Child1Dto> {
}
