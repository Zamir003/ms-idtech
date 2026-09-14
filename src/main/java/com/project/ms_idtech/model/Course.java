package com.project.ms_idtech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
@SQLDelete(sql = "UPDATE courses SET record_status='DELETED' WHERE id=?")
@SQLRestriction("record_status <> 'DELETED'")
public class Course extends BaseEntity {
    // ("record_status = 'ACTIVE'") or
    //("record_status NOT IN ('DELETED', 'INACTIVE')")
    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus courseStatus = CourseStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "record_status")
    private RecordStatus recordStatus = RecordStatus.ACTIVE;

    @ManyToMany
    @JoinTable(
            name = "course_teachers",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> teachers = new HashSet<>();
}