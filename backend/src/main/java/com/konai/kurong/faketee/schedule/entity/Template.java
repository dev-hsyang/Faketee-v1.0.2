package com.konai.kurong.faketee.schedule.entity;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TMP")
@Entity
public class Template extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "SCH_TYPE_ID")
    private ScheduleType scheduleType;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
    private List<TemplateDepartment> templateDepartments = new ArrayList<>();

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
    private List<TemplatePosition> templatePositions = new ArrayList<>();

    public void setTemplateDepartments(List<TemplateDepartment> list){

        this.templateDepartments = list;
    }

    public void setTemplatePositions(List<TemplatePosition> list){

        this.templatePositions = list;
    }
}
