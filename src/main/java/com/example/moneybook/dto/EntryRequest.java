package com.example.moneybook.dto;

import com.example.moneybook.entity.EntryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * 내역 등록/수정 요청 JSON을 담는 그릇.
 * JSON 칸 이름과 필드 이름이 같아야 Spring이 자동으로 채워준다.
 * id는 DB가 정하므로 일부러 없다.
 */
public record EntryRequest(
        @NotNull // 빠지면 거절
        EntryType type,
        @Positive // 0 이하 거절
        long amount,
        @NotBlank
        @Size(max = 50)
        String category,
        @Size(max = 255)
        String memo,
        @NotNull
        LocalDate date
){

}
