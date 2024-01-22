package com.gam.api.service.admin;


import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.admin.magazine.request.MagazineRequestDTO;
import com.gam.api.dto.admin.magazine.response.MagazineListResponseDTO;

import com.gam.api.entity.Magazine;
import com.gam.api.entity.Question;;
import com.gam.api.entity.Work;
import com.gam.api.repository.MagazineRepository;
import com.gam.api.repository.QuestionRepository;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.WorkRepository;
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
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final WorkRepository workRepository;

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
    public void createMagazine(MagazineRequestDTO request) {
        val mainPhotoCount = request.magazinePhotos().size();
        if (mainPhotoCount > 4) {
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

        request.questions().stream()
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
    }

    @Override
    public void editMagazine(Long magazineId, MagazineRequestDTO request) {
        val magazine = magazineRepository.findById(magazineId)
                .orElseThrow(()-> new EntityNotFoundException());

        if (magazine.getMagazineTitle() != request.title()) {
            magazine.setMagazineTitle(request.title());
        }
        if (magazine.getIntroduction() != request.magazineIntro()) {
            magazine.setIntroduction(request.magazineIntro());
        }
        if (magazine.getInterviewPerson() != request.interviewPerson()) {
            magazine.setInterviewPerson(request.interviewPerson());
        }

        val magazinePhotos = request.magazinePhotos().toArray(new String[request.magazinePhotos().size()]);
        magazine.setMagazine_photos(magazinePhotos);
        magazineRepository.save(magazine);

        val currentQuestions = magazine.getQuestions();
        val requestQuestions = request.questions().stream()
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
                    return question;
                }).collect(Collectors.toList());

        checkEntityDiff(currentQuestions, requestQuestions);

        if (currentQuestions.size() < requestQuestions.size()) {
            val createCount = requestQuestions.size() - currentQuestions.size();
            val currentQuestionSize = currentQuestions.size();
            for (int i = 0 ; i<createCount; i++) {
                val createQuestion = questionRepository.save(requestQuestions.get(currentQuestionSize+i));
                createQuestion.setMagazine(magazine);
            }
        }
        questionRepository.saveAll(currentQuestions);
    }
    private List<Question> checkEntityDiff(List<Question> currentQuestions, List<Question> requestQuestions) {
        val changeSize = currentQuestions.size();
        for (int i = 0; i<changeSize; i++) {
            if (!currentQuestions.get(i).equals(requestQuestions.get(i))) {
                editChangeQuestion(currentQuestions.get(i), requestQuestions.get(i));
            }
        }
        return currentQuestions;
    }

    private Question editChangeQuestion(Question currentEntity, Question requestEntity) {
        if (currentEntity.getQuestionOrder() != requestEntity.getQuestionOrder()) {
            currentEntity.setQuestionOrder(requestEntity.getQuestionOrder());
        }
        if (currentEntity.getQuestion() != requestEntity.getQuestion()) {
            currentEntity.setQuestion(requestEntity.getQuestion());
        }
        if (currentEntity.getAnswer() != requestEntity.getAnswer()) {
            currentEntity.setAnswer(requestEntity.getAnswer());
        }
        if (currentEntity.getAnswerImage() != requestEntity.getAnswerImage()) {
            currentEntity.setAnswerImage(requestEntity.getAnswerImage());
        }
        if (currentEntity.getImageCaption() != requestEntity.getImageCaption()) {
            currentEntity.setImageCaption(requestEntity.getImageCaption());
        }
        return currentEntity;
    }

    @Override
    public void deleteUserAccount(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
        List<Work> works = workRepository.findAllByUserId(userId);
        for(Work work : works){ // 유저가 작성한 작업물 삭제
            workRepository.deleteById(work.getId());
        }

        userRepository.deleteById(userId);
    }
}
