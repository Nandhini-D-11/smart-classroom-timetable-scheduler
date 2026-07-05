package com.nandhini.smartclassroom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String department;
    private Integer semester;
    private String subject;
    private String faculty;
    private String classroom;
    private String day;
    private String timeSlot;
}