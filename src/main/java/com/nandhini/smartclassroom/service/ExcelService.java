package com.nandhini.smartclassroom.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.nandhini.smartclassroom.entity.Timetable;

@Service
public class ExcelService {

    public ByteArrayInputStream generateExcel(List<Timetable> timetables)
            throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Timetable");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Department");
        header.createCell(1).setCellValue("Semester");
        header.createCell(2).setCellValue("Subject");
        header.createCell(3).setCellValue("Faculty");
        header.createCell(4).setCellValue("Classroom");
        header.createCell(5).setCellValue("Day");
        header.createCell(6).setCellValue("Time Slot");

        int rowNum = 1;

        for (Timetable t : timetables) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(t.getDepartment());
            row.createCell(1).setCellValue(t.getSemester());
            row.createCell(2).setCellValue(t.getSubject());
            row.createCell(3).setCellValue(t.getFaculty());
            row.createCell(4).setCellValue(t.getClassroom());
            row.createCell(5).setCellValue(t.getDay());
            row.createCell(6).setCellValue(t.getTimeSlot());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}