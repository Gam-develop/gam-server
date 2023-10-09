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
@Table(name = "\"Report\"")
public class Report extends TimeStamped {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Column(name = "content")
    private String content;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "target_user_id")
//    private User targetUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;

    @Builder
    public Report(User targetUser, String content, Work work){
        this.status = ReportStatus.PROCEEDING;
//        setUser(targetUser);
        this.content = content;
        this.work = work;
    }
//    private void setUser(User targetUser) {
//        this.targetUser = targetUser;
//        targetUser.setReported(this);
//    }
}
