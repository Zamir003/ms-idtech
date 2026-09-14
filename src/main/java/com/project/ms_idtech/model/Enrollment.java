package com.project.ms_idtech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "enrollments")
@SQLDelete(sql = "UPDATE enrollments SET record_status='DELETED' WHERE id=?")
@SQLRestriction("record_status <> 'DELETED'")
public class Enrollment extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus enrollmentStatus = EnrollmentStatus.PENDING_PAYMENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "record_status")
    private RecordStatus recordStatus = RecordStatus.ACTIVE;
}