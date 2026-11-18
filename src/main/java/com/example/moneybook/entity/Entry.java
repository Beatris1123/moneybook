package com.example.moneybook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 수입·지출 내역 한 건. DB의 entry 테이블 한 행과 1:1로 대응한다.
 */
@Entity     // JPA가 관리하는 엔티티 → ddl-auto=update 가 이 클래스를 보고 entry 테이블을 만든다
@Getter     // 모든 필드의 getter 자동 생성 (JSON 응답 변환에도 쓰임)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA가 DB에서 읽어온 값을 채울 때 쓰는 기본 생성자. 외부에서 빈 객체를 못 만들게 protected
// @Setter는 일부러 안 붙임: 값 변경은 아래 update() 한 곳에서만 하도록
public class Entry {

    @Id                                                  // 기본 키(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // MySQL AUTO_INCREMENT로 id 자동 증가
    private Long id;                                     // 저장 전에는 null이라 래퍼 타입 Long

    @Enumerated(EnumType.STRING) // enum을 숫자(0,1)가 아닌 이름("INCOME")으로 저장
    @Column(nullable = false)    // NOT NULL 제약
    private EntryType type;      // 수입 / 지출

    @Column(nullable = false)
    private long amount;         // 금액(원). 원화는 소수점이 없으니 long. double은 오차가 생겨서 금지

    @Column(nullable = false)
    private String category;     // 카테고리 (식비, 교통, 월급 ...)

    private String memo;         // 메모. 선택 입력이라 null 허용 (nullable 기본값이 true)

    @Column(nullable = false)
    private LocalDate date;      // 사용한 날짜 (시간은 필요 없어서 LocalDate). DB 타입은 DATE

    /**
     * 새 내역 생성용 생성자. id는 DB가 정하므로 받지 않는다.
     */
    public Entry(EntryType type, long amount, String category, String memo, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.memo = memo;
        this.date = date;
    }

    /**
     * 내역 수정. PUT /entries/{id} 에서 사용.
     * @Transactional 안에서 이 메서드로 값만 바꾸면, 트랜잭션이 끝날 때 JPA가 바뀐 걸 감지해서
     * 자동으로 UPDATE 쿼리를 날린다 (변경 감지). 그래서 save()를 다시 부를 필요가 없다.
     * 파라미터를 DTO가 아니라 값으로 받는 이유: 엔티티가 웹 계층(DTO)에 의존하지 않게 하려고.
     */
    public void update(EntryType type, long amount, String category, String memo, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.memo = memo;
        this.date = date;
    }
}
