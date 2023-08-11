package com.gam.api.dto.admin.magazine.response;

import com.gam.api.entity.Magazine;
import com.gam.api.entity.MagazinePhoto;
import com.gam.api.entity.Question;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record MagazineCreateRequestDTO (
        Long magazineId,
        List<String> magazinePhotos,
        String magazineIntro,
        List<QuestionVO> questions
){
    public static MagazineCreateRequestDTO of(Magazine magazine, List<MagazinePhoto> magazinePhotos, List<Question> questions) {
        return MagazineCreateRequestDTO.builder()
                .magazineId(magazine.getId())
                .magazinePhotos(magazinePhotos.stream()
                        .map((magazinePhoto) -> magazinePhoto.getUrl())
                        .collect(Collectors.toList()))
                .magazineIntro(magazine.getIntroduction())
                .questions(questions.stream()
                        .map((question) -> QuestionVO.of(question))
                        .collect(Collectors.toList()))
                .build();
    }
}

@Builder
record QuestionVO(
        Long questionId,
        int questionOrder,
        String answer,
        String answerImage,
        String imageCaption
) {
    public static QuestionVO of(Question question) {
        return QuestionVO.builder()
                .questionId(question.getId())
                .questionOrder(question.getQuestionOrder())
                .answer(question.getAnswer())
                .answerImage(question.getAnswerImage())
                .imageCaption(question.getImageCaption())
                .build();
    }
}
