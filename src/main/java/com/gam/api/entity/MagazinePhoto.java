package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"MagazinePhoto\"")
public class MagazinePhoto extends TimeStamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    @Column(name = "url")
    private String url;

    @Builder
    public MagazinePhoto(String url){
        this.url = url;
    }

    public void setMagazine(Magazine magazine) {
        if(this.magazine != null) {
            System.out.println("not null");
            System.out.println(this.magazine.getMagazineTitle());
            this.magazine.getMagazinePhotos().remove(this);
        }
        this.magazine = magazine;
        magazine.getMagazinePhotos().add(this);
    }


}
