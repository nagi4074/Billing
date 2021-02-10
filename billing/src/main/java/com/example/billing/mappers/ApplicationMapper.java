package com.example.billing.mappers;

import java.util.List;

import com.example.billing.domains.Application;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper {
    List<Application> all();
    
}
