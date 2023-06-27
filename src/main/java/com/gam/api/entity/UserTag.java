package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"UserTag\"")
@Entity
public class UserTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_tag_id")
    private Long id;

    @Column(name = "ui_ux")
    private boolean ui_uxDesign;

    @Column(name = "bi_bx")
    private boolean bi_bxDesign;

    @Column(name = "industrial")
    private boolean industrialDesign;

    @Column(name = "threeD")
    private boolean threeDDesign;

    @Column(name = "graphic")
    private boolean graphicDesign;

    @Column(name = "package")
    private boolean packageDesign;

    @Column(name = "motion")
    private boolean motionDesign;

    @Column(name = "illust")
    private boolean illustDesign;

    @Column(name = "edit")
    private boolean editDesign;

    @Column(name = "fashion")
    private boolean fashionDesign;

    @Column(name = "space")
    private boolean spaceDesign;

    @Column(name = "character")
    private boolean characterDesign;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
