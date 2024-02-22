package com.gam.api.domain.report.entity;

import com.gam.api.common.entity.superclass.TimeStamped;
import com.gam.api.domain.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @Column(name = "work_Id")
    private Long workId;

    @Builder
    public Report(User targetUser, String content, Long workId){
        this.status = ReportStatus.PROCEEDING;
        setUser(targetUser);
        this.content = content;
        this.workId = workId;
    }

    private void setUser(User targetUser) {
        if (Objects.nonNull(this.targetUser)) {
            this.targetUser.getReported().remove(this);
        }
        this.targetUser = targetUser;
        targetUser.getReported().add(this);
    }
}
