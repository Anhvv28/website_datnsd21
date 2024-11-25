package com.fpt.niceshoes.dto.response.statistic;

import org.springframework.beans.factory.annotation.Value;

public interface StatisticalDayResponse {

    @Value("#{target.totalBillToday}")
    Integer getTotalBillToday();
    @Value("#{target.totalBillAmountToday}")
    Integer getTotalBillAmountToday();
}
