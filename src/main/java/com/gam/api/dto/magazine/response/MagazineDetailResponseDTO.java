package com.gam.api.dto.magazine.response;

import com.gam.api.entity.Magazine;
import com.gam.api.entity.Question;
import lombok.Builder;

import java.util.List;

@Builder
public record MagazineDetailResponseDTO(
        Long magazineId,
        List<String> magazinePhotos,
        String magazineIntro,
        List<MagazineDetailResponseVO> questions,
        String magazineUrl
) {
    public static MagazineDetailResponseDTO of(
           Magazine magazine,
           String magazineBaseUrl
    ) {
        return MagazineDetailResponseDTO.builder()
                .magazineId(magazine.getId())
                .magazinePhotos(List.of(magazine.getMagazine_photos()))
                .magazineIntro(magazine.getIntroduction())
                .questions(magazine.getQuestions().stream().map(MagazineDetailResponseVO::of).toList())
                .magazineUrl(magazineBaseUrl + magazine.getId())
                .build();
    }
}

@Builder
record MagazineDetailResponseVO(
        Long questionId,
        int questionOrder,
        String question,
        String answer,
        String answerImage,
        String imageCaption
) {
    public static MagazineDetailResponseVO of(Question question) {
        return MagazineDetailResponseVO.builder()
                .questionId(question.getId())
                .questionOrder(question.getQuestionOrder())
                .question(question.getQuestion())
                .answer(question.getAnswer())
                .answerImage(question.getAnswerImage())
                .imageCaption(question.getImageCaption())
                .build();
    }
}
