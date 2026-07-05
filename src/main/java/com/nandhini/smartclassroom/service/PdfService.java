package com.nandhini.smartclassroom.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.nandhini.smartclassroom.entity.Timetable;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;

@Service
public class PdfService {

    public ByteArrayInputStream generatePdf(List<Timetable> timetables) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);

Paragraph title = new Paragraph("SMART CLASSROOM TIMETABLE", titleFont);
title.setAlignment(Element.ALIGN_CENTER);

document.add(title);
document.add(new Paragraph(" "));

PdfPTable table = new PdfPTable(7);
table.setWidthPercentage(100);

table.addCell(new PdfPCell(new Phrase("Department")));
table.addCell(new PdfPCell(new Phrase("Semester")));
table.addCell(new PdfPCell(new Phrase("Subject")));
table.addCell(new PdfPCell(new Phrase("Faculty")));
table.addCell(new PdfPCell(new Phrase("Classroom")));
table.addCell(new PdfPCell(new Phrase("Day")));
table.addCell(new PdfPCell(new Phrase("Time Slot")));

for (Timetable tt : timetables) {

    table.addCell(tt.getDepartment());
    table.addCell(String.valueOf(tt.getSemester()));
    table.addCell(tt.getSubject());
    table.addCell(tt.getFaculty());
    table.addCell(tt.getClassroom());
    table.addCell(tt.getDay());
    table.addCell(tt.getTimeSlot());

}

document.add(table);

document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}