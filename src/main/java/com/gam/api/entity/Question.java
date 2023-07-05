package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    @Column(name = "order")
    private Long order;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "answer_image")
    private String answerImage;

    @Column(name = "image_caption")
    private String imageCaption;
}
