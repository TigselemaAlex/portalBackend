package com.example.portalbackend.domain.entity;

import com.example.portalbackend.util.enumerate.ConvocationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE convocation SET active = false, updated_at = now() WHERE  id = ? ")
public class Convocation extends AbstractEntity{

    private String subject;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String place;
    private String code;
    private Calendar date;
    private ConvocationType type;
    private Calendar attendanceDeadline;
    private Boolean finalized;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private User updatedBy;
    @OneToMany(mappedBy = "convocation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ConvocationParticipant> participants;
    @OneToMany(mappedBy = "convocation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AssemblyQuestion> assemblyQuestions;
    @OneToMany(mappedBy = "convocation")
    private List<Minutes> minutes;

}
