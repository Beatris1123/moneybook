package com.example.moneybook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 월별·카테고리별 예산. DB의 budget 테이블 한 행과 대응한다.
 * 예: "2026-11" 달의 "식비" 예산 = 500,000원
 */
@Entity
// 같은 달 + 같은 카테고리 예산이 두 줄 생기지 않도록 DB가 직접 막는 unique 제약.
// 코드에서 실수해도 데이터는 안전하고, 위반 시 Spring은 DataIntegrityViolationException을 던진다.
// columnNames에는 자바 필드명이 아니라 "DB 컬럼명"을 적는다 (여기서는 둘이 같음).
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"month", "category"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용 기본 생성자 (Entry와 같은 이유)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL AUTO_INCREMENT
    private Long id;

    // "2026-11" 형식의 문자열.
    // YearMonth 타입으로 저장하려면 변환기(AttributeConverter)가 필요해서, 단순하게 String으로 저장한다.
    // 컨트롤러에서는 YearMonth로 받고 month.toString()으로 바꿔 넘기면 항상 같은 형식이 된다.
    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    private String category;  // Entry.category와 같은 이름을 써야 요약에서 서로 매칭된다

    @Column(nullable = false)
    private long amount;      // 예산 금액(원). 0원 예산도 허용

    /** 새 예산 생성용. id는 DB가 정한다. */
    public Budget(String month, String category, long amount) {
        this.month = month;
        this.category = category;
        this.amount = amount;
    }

    /**
     * 예산 금액 변경. PUT /budgets 에서 이미 있는 예산을 덮어쓸 때 사용.
     * month, category는 예산을 구분하는 "열쇠"라서 바꾸지 않고 금액만 바꾼다.
     * Entry.update()와 마찬가지로 @Transactional 안에서 호출하면 save() 없이 UPDATE 된다.
     */
    public void changeAmount(long amount) {
        this.amount = amount;
    }
}
