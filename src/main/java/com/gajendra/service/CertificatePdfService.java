
package com.gajendra.service;

import com.gajendra.entity.CourseCompletion;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class CertificatePdfService {

    public byte[] generateCertificatePdf(CourseCompletion completion) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD, 26);

            Font headingFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD, 18);

            Font normalFont = FontFactory.getFont(
                    FontFactory.HELVETICA, 14);

            Font numberFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD, 12);

            Paragraph title = new Paragraph(
                    "CERTIFICATE OF COMPLETION", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            Paragraph heading = new Paragraph(
                    "This certificate is proudly presented to",
                    normalFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            heading.setSpacingAfter(20);
            document.add(heading);

            String studentName =
                    completion.getStudent().getStudentName();

            Paragraph name = new Paragraph(studentName, headingFont);
            name.setAlignment(Element.ALIGN_CENTER);
            name.setSpacingAfter(20);
            document.add(name);

            Paragraph message = new Paragraph(
                    "for successfully completing the course",
                    normalFont);
            message.setAlignment(Element.ALIGN_CENTER);
            message.setSpacingAfter(15);
            document.add(message);

            String courseName = "Course";

            if (completion.getAdmission().getCourse() != null) {
               
                courseName = completion.getAdmission().getCourse().getCourseName();
            }

            Paragraph course = new Paragraph(courseName, headingFont);
            course.setAlignment(Element.ALIGN_CENTER);
            course.setSpacingAfter(30);
            document.add(course);

            String completionDate = completion.getCompletionDate()
                    .format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));

            Paragraph date = new Paragraph(
                    "Completion Date: " + completionDate,
                    normalFont);
            date.setAlignment(Element.ALIGN_CENTER);
            date.setSpacingAfter(25);
            document.add(date);

            Paragraph certificateNumber = new Paragraph(
                    "Certificate No.: "
                            + completion.getCertificateNumber(),
                    numberFont);
            certificateNumber.setAlignment(Element.ALIGN_CENTER);
            certificateNumber.setSpacingAfter(20);
            document.add(certificateNumber);

            if (completion.getRemarks() != null
                    && !completion.getRemarks().isBlank()) {

                Paragraph remarks = new Paragraph(
                        "Remarks: " + completion.getRemarks(),
                        normalFont);
                remarks.setAlignment(Element.ALIGN_CENTER);
                document.add(remarks);
            }

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to generate certificate PDF.", e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}
