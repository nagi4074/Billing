package com.example.billing.mappers;

import java.util.List;

import com.example.billing.domains.Application;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper {
    //アプリ一覧を表示
    List<Application> applicationAll();
    //総課金額を表示
    List<Application> totalBilling();
    
}
