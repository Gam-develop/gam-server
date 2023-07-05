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
@Table(name = "\"Report\"")
public class Report extends TimeStamped {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;
}
