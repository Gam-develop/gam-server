package com.gam.api.domain.admin.dto.magazine.response;

import com.gam.api.domain.magazine.entity.Magazine;
import com.gam.api.domain.magazine.entity.Question;
import java.util.List;
import lombok.Builder;

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
                .magazinePhotos(List.of(magazine.getMagazine_photos()))
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
