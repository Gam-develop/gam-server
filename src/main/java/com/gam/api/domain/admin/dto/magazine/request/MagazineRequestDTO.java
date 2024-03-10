package com.gam.api.domain.admin.dto.magazine.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record MagazineRequestDTO(
        @NotNull(message = "magazine title은 null일 수 없습니다.")
        @NotBlank(message = "magazine title은 빈스트링 일 수 없습니다.")
        String title,
        @NotNull(message = "magazine photo는 null일 수 없습니다.")
        List<String> magazinePhotos,
        @NotNull(message = "magazine intro는 null일 수 없습니다.")
        @NotBlank(message = "magazine intro는 빈스트링 일 수 없습니다.")
        String magazineIntro,
        @NotNull(message = "magazine interviewPerson은 null일 수 없습니다.")
        @NotBlank(message = "magazine interviewPerson은 빈스트링 일 수 없습니다.")
        String interviewPerson,
        @NotNull(message = "magazine questions는 null일 수 없습니다.")
        List<QuestionVO> questions
) {
    @Builder
    public record QuestionVO(int questionOrder, String question, String answer, String answerImage, String imageCaption) {}
}


