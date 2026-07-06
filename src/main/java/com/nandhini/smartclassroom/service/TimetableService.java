package com.nandhini.smartclassroom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandhini.smartclassroom.entity.Timetable;
import com.nandhini.smartclassroom.repository.TimetableRepository;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    public String saveTimetable(Timetable timetable) {

        if (timetable.getId() == null) {

            boolean facultyExists =
                    timetableRepository.existsByFacultyAndDayAndTimeSlot(
                            timetable.getFaculty(),
                            timetable.getDay(),
                            timetable.getTimeSlot());

            if (facultyExists) {
                return "Faculty is already assigned at this time!";
            }

            boolean classroomExists =
                    timetableRepository.existsByClassroomAndDayAndTimeSlot(
                            timetable.getClassroom(),
                            timetable.getDay(),
                            timetable.getTimeSlot());

            if (classroomExists) {
                return "Classroom is already occupied at this time!";
            }
        }
        
        boolean duplicate =
        timetableRepository.existsByDepartmentAndSemesterAndSubjectAndFacultyAndClassroomAndDayAndTimeSlot(
                timetable.getDepartment(),
                timetable.getSemester(),
                timetable.getSubject(),
                timetable.getFaculty(),
                timetable.getClassroom(),
                timetable.getDay(),
                timetable.getTimeSlot());

if (duplicate) {
    return "Timetable already exists!";
}


        timetableRepository.save(timetable);

        return "Timetable saved successfully!";
    }

    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }

    public Timetable getTimetableById(Long id) {

        Optional<Timetable> timetable =
                timetableRepository.findById(id);

        return timetable.orElse(new Timetable());
    }

    public List<Timetable> searchByDepartment(String department) {

        return timetableRepository.findByDepartment(department);
    }
    

public long getTotalTimetables() {
    return timetableRepository.count();
}

public long getTotalFaculty() {
    return timetableRepository.countDistinctByFacultyIsNotNull();
}

public long getTotalDepartments() {
    return timetableRepository.countDistinctByDepartmentIsNotNull();
}
}