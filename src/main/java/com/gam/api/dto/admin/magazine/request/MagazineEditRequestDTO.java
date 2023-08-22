package com.gam.api.dto.admin.magazine.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record MagazineEditRequestDTO(
        @NotNull
        @NotBlank
        String title,
        @NotNull
        @NotBlank
        List<String> magazinePhotos,

        @NotNull
        @NotBlank
        String magazineIntro,
        @NotNull
        @NotBlank
        String interviewPerson,
        @NotNull
        @NotBlank
        List<QuestionVO> questions
) {
    @Builder
    public record QuestionVO( int questionOrder, String question, String answer, String answerImage, String imageCaption) {}
}


