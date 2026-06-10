package com.example.paretchilddbrelation.mapper;

import com.example.paretchilddbrelation.dto.Child2Dto;
import com.example.paretchilddbrelation.entity.Child2;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Child2Mapper extends BaseMapper<Child2, Child2Dto> {
}
