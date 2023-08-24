package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"Question\"")
public class Question extends TimeStamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    @Column(name = "question_order")
    private int questionOrder;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "answer_image")
    private String answerImage;

    @Column(name = "image_caption")
    private String imageCaption;

    public void setMagazine(Magazine magazine) {
        if(this.magazine != null) {
            this.magazine.getQuestions().remove(this);
        }
        this.magazine = magazine;
        magazine.getQuestions().add(this);
    }

    @Builder
    public Question(int questionOrder, String question, String answer) {
        this.questionOrder = questionOrder;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return getQuestionOrder() == question1.getQuestionOrder() && Objects.equals(getQuestion(), question1.getQuestion()) && Objects.equals(getAnswer(), question1.getAnswer()) && Objects.equals(getAnswerImage(), question1.getAnswerImage()) && Objects.equals(getImageCaption(), question1.getImageCaption());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionOrder(), getQuestion(), getAnswer(), getAnswerImage(), getImageCaption());
    }
}
