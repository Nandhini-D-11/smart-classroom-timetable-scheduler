package com.nandhini.smartclassroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.nandhini.smartclassroom.entity.Timetable;
import com.nandhini.smartclassroom.service.TimetableService;
import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.nandhini.smartclassroom.service.PdfService;
import com.nandhini.smartclassroom.service.ExcelService;

@Controller
public class HomeController {

    @Autowired
    private TimetableService timetableService;
    @Autowired
private PdfService pdfService;
@Autowired
private ExcelService excelService;
@GetMapping("/login")
public String loginPage() {
    return "login";
}
@PostMapping("/login")
public String login(@RequestParam String username,
                    @RequestParam String password,
                    Model model) {

    if (username.equals("admin") && password.equals("Smart@2026")) {
        return "welcome";
    }

    model.addAttribute("error", "Invalid Username or Password!");

    return "login";
}
@GetMapping("/logout")
public String logout() {
    return "redirect:/login";
}

    @GetMapping("/")
public String home(Model model) {

    model.addAttribute("timetable", new Timetable());
    model.addAttribute("timetables", timetableService.getAllTimetables());

    model.addAttribute("totalTimetables",
            timetableService.getTotalTimetables());

    model.addAttribute("totalFaculty",
            timetableService.getTotalFaculty());

    model.addAttribute("totalDepartments",
            timetableService.getTotalDepartments());

    return "index";
}

    @PostMapping("/save")
    public String saveTimetable(@ModelAttribute Timetable timetable,
                                Model model) {

        String message = timetableService.saveTimetable(timetable);

        model.addAttribute("message", message);
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("timetables",
                timetableService.getAllTimetables());

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteTimetable(@PathVariable Long id) {

        timetableService.deleteTimetable(id);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTimetable(@PathVariable Long id,
                                Model model) {

        model.addAttribute("timetable",
                timetableService.getTimetableById(id));

        model.addAttribute("timetables",
                timetableService.getAllTimetables());

        return "index";
    }

    @GetMapping("/generate")
    public String generateTimetable() {

        Timetable t1 = new Timetable();
        t1.setDepartment("CSE");
        t1.setSemester(5);
        t1.setSubject("DBMS");
        t1.setFaculty("Priya");
        t1.setClassroom("A101");
        t1.setDay("Monday");
        t1.setTimeSlot("9-10 AM");

        timetableService.saveTimetable(t1);

        Timetable t2 = new Timetable();
        t2.setDepartment("IT");
        t2.setSemester(3);
        t2.setSubject("Data Structures");
        t2.setFaculty("Kumar");
        t2.setClassroom("B201");
        t2.setDay("Tuesday");
        t2.setTimeSlot("10-11 AM");

        timetableService.saveTimetable(t2);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchTimetable(@RequestParam String department,
                                  Model model) {

        model.addAttribute("timetable", new Timetable());

        model.addAttribute("timetables",
                timetableService.searchByDepartment(department));

        return "index";
    }
    @GetMapping("/pdf")
public ResponseEntity<InputStreamResource> downloadPdf() {

    ByteArrayInputStream pdf =
            pdfService.generatePdf(timetableService.getAllTimetables());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition",
            "inline; filename=timetable.pdf");

    return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(pdf));
}
@GetMapping("/excel")
public ResponseEntity<InputStreamResource> downloadExcel() throws Exception {

    ByteArrayInputStream excel =
            excelService.generateExcel(timetableService.getAllTimetables());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition",
            "attachment; filename=SmartClassroomTimetable.xlsx");

    return ResponseEntity.ok()
            .headers(headers)
            .body(new InputStreamResource(excel));}
@GetMapping("/about")
public String about(){
    return "about";
            }
@GetMapping("/contact")
public String contact(){
    return "contact";
}
}
