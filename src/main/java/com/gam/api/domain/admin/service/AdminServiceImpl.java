package com.gam.api.domain.admin.service;


import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.domain.admin.dto.magazine.request.MagazineRequestDTO;
import com.gam.api.domain.admin.dto.magazine.request.MagazineRequestDTO.QuestionVO;
import com.gam.api.domain.admin.dto.magazine.response.MagazineListResponseDTO;

import com.gam.api.domain.admin.dto.magazine.response.MagazineDetailResponseDTO;
import com.gam.api.domain.magazine.entity.Magazine;
import com.gam.api.domain.magazine.entity.Question;
import com.gam.api.domain.work.entity.Work;
import com.gam.api.domain.magazine.repository.MagazineRepository;
import com.gam.api.domain.magazine.repository.QuestionRepository;
import com.gam.api.domain.user.repository.UserRepository;
import com.gam.api.domain.work.repository.WorkRepository;
import java.util.ArrayList;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    private final UserRepository userRepository;
    private final WorkRepository workRepository;
    private final int MAX_MAGAZINE_PHOTO_COUNT = 4;

    @Override
    public List<MagazineListResponseDTO> getMagazines() {
        return magazineRepository.findAllByOrderByModifiedAtDescCreatedAtDesc().stream()
                .map(MagazineListResponseDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMagazine(Long magazineId) {
        findMagazine(magazineId);

        magazineRepository.deleteById(magazineId);
    }

    @Transactional
    @Override
    public void createMagazine(MagazineRequestDTO request) {
        val mainPhotoCount = request.magazinePhotos().size();
        if (mainPhotoCount > MAX_MAGAZINE_PHOTO_COUNT) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_MAIN_PHOTOS_COUNT.getMessage());
        }

        val magazine = Magazine.builder()
                .thumbNail(request.magazinePhotos().get(0))
                .magazineTitle(request.title())
                .introduction(request.magazineIntro())
                .interviewPerson(request.interviewPerson())
                .build();
        magazineRepository.save(magazine);

        val magazinePhotos = request.magazinePhotos().toArray(new String[request.magazinePhotos().size()]);
        magazine.setMagazine_photos(magazinePhotos);

        toQuestionEntity(magazine, request.questions());
    }

    @Override
    @Transactional
    public void editMagazine(Long magazineId, MagazineRequestDTO request) {
        val magazine = magazineRepository.findById(magazineId)
                .orElseThrow(() -> new EntityNotFoundException());

        magazine.setMagazineTitle(request.title());
        magazine.setIntroduction(request.magazineIntro());
        magazine.setInterviewPerson(request.interviewPerson());

        val magazinePhotos = request.magazinePhotos().toArray(new String[request.magazinePhotos().size()]);
        magazine.setMagazine_photos(magazinePhotos);
        magazineRepository.save(magazine);

        List<Question> currentQuestions = new ArrayList<>(magazine.getQuestions());
        Collections.sort(request.questions(), (q1, q2) -> Integer.compare(q1.questionOrder(), q2.questionOrder()));

        int requestQuestionSize = request.questions().size();

        if(requestQuestionSize <= currentQuestions.size()) { // 질문이 삭제 됐을 경우
            for (int i = 0; i < requestQuestionSize; i++) {
                updateQuestionDetails(currentQuestions.get(i), request.questions().get(i));
            }
            if(requestQuestionSize < currentQuestions.size()) {
                magazine.getQuestions().removeAll(currentQuestions.subList(request.questions().size(), currentQuestions.size()));
            }
            return;
        }

        for (int i = 0; i < currentQuestions.size(); i++) { // 질문이 추가 될 경우
            updateQuestionDetails(currentQuestions.get(i), request.questions().get(i));
        }
        List<QuestionVO> createQuestion = request.questions().subList(currentQuestions.size(), request.questions().size());
        toQuestionEntity(magazine, createQuestion);
    }

    private void updateQuestionDetails(Question oldQuestion, QuestionVO newQuestion) {
        oldQuestion.setQuestionOrder(newQuestion.questionOrder());
        oldQuestion.setQuestion(newQuestion.question());
        oldQuestion.setAnswer(newQuestion.answer());
        oldQuestion.setAnswerImage(newQuestion.answerImage());
        oldQuestion.setImageCaption(newQuestion.imageCaption());
    }

    private List<Question> toQuestionEntity(Magazine magazine, List<QuestionVO> request) {
        return request.stream()
                .map((questionVO) -> {
                    Question question = Question.builder()
                            .questionOrder(questionVO.questionOrder())
                            .question(questionVO.question())
                            .answer(questionVO.answer())
                            .build();

                    if (questionVO.answerImage().isBlank()) {
                        question.setAnswerImage(null);
                        question.setImageCaption(null);
                    } else {
                        question.setAnswerImage(questionVO.answerImage());
                        question.setImageCaption(questionVO.imageCaption());
                    }
                    question.setMagazine(magazine);
                    return question;
                }).collect(Collectors.toList());
    }


    @Override
    public void deleteUserAccount(Long userId) {
        List<Work> works = workRepository.findAllByUserId(userId);
        for(Work work : works){ // 유저가 작성한 작업물 삭제
            workRepository.deleteById(work.getId());
        }

        userRepository.deleteById(userId);
    }

    @Override
    public MagazineDetailResponseDTO getMagazineDetail(Long magazineId, Long userId) {
        val magazine = findMagazine(magazineId);

        return MagazineDetailResponseDTO.of(magazine);
    }

    private Magazine findMagazine(Long magazineId) {
        return magazineRepository.findById(magazineId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_MAGAZINE.getMessage()));
    }
}
