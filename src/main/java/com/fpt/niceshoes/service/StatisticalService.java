package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.request.bill.BillSearchRequest;
import com.fpt.niceshoes.dto.response.BillResponse;
import com.fpt.niceshoes.dto.response.statistic.StatisticalDayResponse;
import com.fpt.niceshoes.dto.response.statistic.StatisticalMonthlyResponse;
import com.fpt.niceshoes.infrastructure.common.PageableObject;

import java.util.List;

public interface StatisticalService {

    List<StatisticalDayResponse> getAllStatisticalDay();
    List<StatisticalMonthlyResponse> getAllStatisticalMonth();
    PageableObject<BillResponse> getStatisticalByDateRange(BillSearchRequest request);
}
