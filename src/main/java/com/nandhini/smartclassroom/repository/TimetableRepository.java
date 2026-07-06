package com.nandhini.smartclassroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nandhini.smartclassroom.entity.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    boolean existsByFacultyAndDayAndTimeSlot(
        String faculty,
        String day,
        String timeSlot);

boolean existsByClassroomAndDayAndTimeSlot(
        String classroom,
        String day,
        String timeSlot);
boolean existsByDepartmentAndSemesterAndSubjectAndFacultyAndClassroomAndDayAndTimeSlot(
        String department,
        Integer semester,
        String subject,
        String faculty,
        String classroom,
        String day,
        String timeSlot);

    List<Timetable> findByDepartment(String department);
    long countDistinctByFacultyIsNotNull();

long countDistinctByDepartmentIsNotNull();
}