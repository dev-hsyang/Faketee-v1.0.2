package com.konai.kurong.faketee.vacation.entity;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import com.konai.kurong.faketee.vacation.util.RequestVal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VAC_REQ")
@Entity
public class VacRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VAC_REQ_DATE")
    private LocalDate date;

    @Column(name = "ORG_START_TIME")
    private LocalTime originStartTime;

    @Column(name = "VAL")
    private RequestVal val;

    @ManyToOne
    @JoinColumn(name = "VAC_TYPE_ID")
    private VacType vacType;

    @ManyToOne
    @JoinColumn(name = "DRAFT_ID")
    private Draft draft;

    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    private Employee employee;

}
