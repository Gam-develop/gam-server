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
        List<MagazineDetailResponseVO> questions
) {
    public static MagazineDetailResponseDTO of(
           Magazine magazine
    ) {
        return MagazineDetailResponseDTO.builder()
                .magazineId(magazine.getId())
                .magazinePhotos(magazine.getMagazinePhotos().stream().map(magazinePhoto -> magazinePhoto.getUrl()).toList())
                .magazineIntro(magazine.getIntroduction())
                .questions(magazine.getQuestions().stream().map(MagazineDetailResponseVO::of).toList())
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
