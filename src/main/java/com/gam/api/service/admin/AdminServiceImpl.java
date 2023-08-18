package com.gam.api.service.admin;


import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.admin.magazine.request.MagazineCreateRequestDTO;
import com.gam.api.dto.admin.magazine.response.MagazineListResponseDTO;

import com.gam.api.entity.Magazine;
import com.gam.api.entity.MagazinePhoto;
import com.gam.api.entity.Question;
import com.gam.api.repository.MagazinePhotoRepository;
import com.gam.api.repository.MagazineRepository;
import com.gam.api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MagazineRepository magazineRepository;
    private final MagazinePhotoRepository magazinePhotoRepository;
    private final QuestionRepository questionRepository;

    @Override
    public List<MagazineListResponseDTO> getMagazines() {
        return magazineRepository.findAllByOrderByModifiedAtDescCreatedAtDesc().stream()
                .map(MagazineListResponseDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMagazine(Long magazineId) {
        magazineRepository.findById(magazineId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_MAGAZINE.getMessage()));

        magazineRepository.deleteById(magazineId);
    }

    @Transactional
    @Override
    public void createMagazine(MagazineCreateRequestDTO request) {
        val mainPhotoCount = request.magazinePhotos().size();
        if(mainPhotoCount >4) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_MAIN_PHOTOS_COUNT.getMessage());
        }

        val magazine = Magazine.builder()
                .thumbNail(request.magazinePhotos().get(0))
                .magazineTitle(request.title())
                .introduction(request.magazineIntro())
                .interviewPerson(request.interviewPerson())
                .build();
        magazineRepository.save(magazine);

        val magazinePhotos = request.magazinePhotos().stream()
                .map((photo)-> {
                    val magazinePhoto = MagazinePhoto.builder()
                            .url(photo)
                            .build();
                    magazinePhoto.setMagazine(magazine);
                    return magazinePhoto;
                }).collect(Collectors.toList());
        magazinePhotoRepository.saveAll(magazinePhotos);

        val questions = request.questions().stream()
                .map((questionVO) -> {
                            Question question = Question.builder()
                                    .questionOrder(questionVO.questionOrder())
                                    .question(questionVO.question())
                                    .answer(questionVO.answer())
                                    .build();

                            if (questionVO.answerImage() == "") {
                                question.setAnswerImage(null);
                                question.setImageCaption(null);
                            } else {
                                question.setAnswerImage(questionVO.answerImage());
                                question.setImageCaption(questionVO.imageCaption());
                            }
                            question.setMagazine(magazine);
                            return question;
                }).collect(Collectors.toList());
        questionRepository.saveAll(questions);

        magazine.setMagazinePhotos(magazinePhotos);
        magazine.setQuestions(questions);
    }
}
