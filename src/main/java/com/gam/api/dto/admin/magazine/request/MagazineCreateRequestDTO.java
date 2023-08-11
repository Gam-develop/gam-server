package com.gam.api.dto.admin.magazine.request;


import lombok.Builder;

import java.util.List;

public record MagazineCreateRequestDTO(
        String title,
        List<String> magazinePhotos,
        String magazineIntro,
        String interviewPerson,
        List<QuestionVO> questions
) {
    @Builder
    public
    record QuestionVO (
            Long questionOrder,
            String question,
            String answer,
            String answerImage,
            String imageCaption
    ) {
    }

}


