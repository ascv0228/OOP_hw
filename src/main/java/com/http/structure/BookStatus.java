package com.http.structure;

public enum BookStatus {
    AVAILABLE, // 未借出
    ON_LOAN, // 已借出

    // OVERDUE, // 逾期未歸還
    // RESERVED, // 預約中
    // RESERVED_NOT_PICKED,// 已預約未取
    // LOST, // 遺失
    // DAMAGED, // 損壞
    // UNDER_REPAIR, // 維修中
    DISCARDED, // 淘汰
    // RESERVED_FOR_PICKUP,// 保留
    // IN_TRANSIT; // 異動中
    UNKNOWN,
}
