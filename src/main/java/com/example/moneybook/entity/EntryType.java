package com.example.moneybook.entity;

/**
 * 내역의 종류.
 * DB에는 Entry의 @Enumerated(EnumType.STRING) 덕분에 "INCOME" / "EXPENSE" 문자열로 저장된다.
 * 그래서 나중에 상수 순서를 바꾸거나 중간에 새 상수를 추가해도 기존 데이터가 안전하다.
 */
public enum EntryType {
    INCOME,  // 수입 (월급, 용돈 등)
    EXPENSE  // 지출 (식비, 교통 등)
}
