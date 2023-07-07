package com.gam.api.dto.magazine.response;

import com.gam.api.entity.MagazinePhoto;
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
            Long magazineId,
            List<MagazinePhoto> magazinePhotos,
            String magazineIntro,
            List<Question> questions
    ) {
        return MagazineDetailResponseDTO.builder()
                .magazineId(magazineId)
                .magazinePhotos(magazinePhotos.stream().map(magazinePhoto -> magazinePhoto.getUrl()).toList())
                .magazineIntro(magazineIntro)
                .questions(questions.stream().map(MagazineDetailResponseVO::of).toList())
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
