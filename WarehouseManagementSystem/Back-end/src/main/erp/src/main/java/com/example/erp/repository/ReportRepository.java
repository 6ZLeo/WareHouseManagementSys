package com.example.erp.repository;

import java.util.Date;
import java.util.Map;

public interface ReportRepository {

    // 统计当前库存总量
    Long getTotalStockQuantity();

    // 统计各类商品的库存数量
    Map<String, Long> getStockQuantityByProduct();

    // 统计指定时间段的出入库数量
//    List<StockMovementReport> getStockMovementReportByDateRange(Date startDate, Date endDate);

    // 其他报表统计的方法...
}
