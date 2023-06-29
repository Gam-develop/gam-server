package com.gam.api.entity;

import com.gam.api.common.ExceptionMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_tag_id")
    private Long id;

    @OneToOne(mappedBy = "userTag")
    private User user;

    @Column(name = "ui_ux")
    private boolean ui_uxDesign;

    @Column(name = "bi_bx")
    private boolean bi_bxDesign;

    @Column(name = "industrial")
    private boolean industrialDesign;

    @Column(name = "threeD")
    private boolean threeDimensionalDesign;

    @Column(name = "graphic")
    private boolean graphicDesign;

    @Column(name = "package")
    private boolean packageDesign;

    @Column(name = "motion")
    private boolean motionDesign;

    @Column(name = "Illustration")
    private boolean IllustrationDesign;

    @Column(name = "edit")
    private boolean editDesign;

    @Column(name = "fashion")
    private boolean fashionDesign;

    @Column(name = "space")
    private boolean spaceDesign;

    @Column(name = "character")
    private boolean characterDesign;

    @Builder
    public UserTag(List<Boolean> booleanList){
        int i = booleanList.size();
        if (i != 12){
            throw new RuntimeException(ExceptionMessage.CONFIG_ERROR.getName());
        }
        this.ui_uxDesign = booleanList.get(0);
        this.bi_bxDesign = booleanList.get(1);
        this.industrialDesign = booleanList.get(2);
        this.threeDimensionalDesign = booleanList.get(3);
        this.graphicDesign = booleanList.get(4);
        this.packageDesign = booleanList.get(5);
        this.motionDesign = booleanList.get(6);
        this.IllustrationDesign = booleanList.get(7);
        this.editDesign = booleanList.get(8);
        this.fashionDesign = booleanList.get(9);
        this.spaceDesign = booleanList.get(10);
        this.characterDesign = booleanList.get(11);
    }


    public UserTag setUserTag(List<Boolean> booleanList){
        int i = booleanList.size();
        if (i != 12){
            throw new RuntimeException(ExceptionMessage.CONFIG_ERROR.getName());
        }
        this.ui_uxDesign = booleanList.get(0);
        this.bi_bxDesign = booleanList.get(1);
        this.industrialDesign = booleanList.get(2);
        this.threeDimensionalDesign = booleanList.get(3);
        this.graphicDesign = booleanList.get(4);
        this.packageDesign = booleanList.get(5);
        this.motionDesign = booleanList.get(6);
        this.IllustrationDesign = booleanList.get(7);
        this.editDesign = booleanList.get(8);
        this.fashionDesign = booleanList.get(9);
        this.spaceDesign = booleanList.get(10);
        this.characterDesign = booleanList.get(11);
        return this;
    }

    public ArrayList<String> formattingUserTag(UserTag this){
        ArrayList<String> result = new ArrayList<>();
        if (this.ui_uxDesign == true) result.add("ui_ux");
        if (this.ui_uxDesign == true) result.add("bi_bx");
        if (this.industrialDesign == true) result.add("industrial");
        if (this.threeDimensionalDesign == true) result.add("threeD");
        if (this.graphicDesign == true) result.add("graphic");
        if (this.packageDesign == true) result.add("package");
        if (this.motionDesign == true) result.add("motion");
        if (this.IllustrationDesign == true) result.add("Illustration");
        if (this.editDesign == true) result.add("edit");
        if (this.fashionDesign == true) result.add("fashion");
        if (this.spaceDesign == true) result.add("space");
        if (this.characterDesign == true) result.add("character");
        return result;
    }
}
