package com.na.student_assgn.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.exception.EmptyStudentListException;
import com.na.student_assgn.model.Student;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PdfGenerator {
	
	@Autowired
	private StudentDAO studentDAO;

	private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

	public void export(HttpServletResponse response) {
		List<Student> allStudents = studentDAO.findAllStudents();
		if (!allStudents.isEmpty()) {
			Document document = new Document(PageSize.A4,36,36,20,20);

			try {
				PdfWriter.getInstance(document, response.getOutputStream());

				document.open();
				
				// Create a Phrase for the heading
				Phrase headingPhrase = new Phrase("Student Details", FontFactory.getFont(FontFactory.TIMES_BOLD, 22));

				// Create a Paragraph and add the heading
				Paragraph headerPara = new Paragraph();
				headerPara.add(headingPhrase);
				headerPara.setAlignment(Element.ALIGN_CENTER); // Center the heading
//				headerPara.setAlignment(Element.ALIGN_MIDDLE);
				// Create a new Paragraph for the image (optional)
				Paragraph imagePara = new Paragraph(); // Optional: Use this for spacing below the heading

				// Create the Image object and scale it
				Image img = Image.getInstance("/home/sathishkumar/Downloads/images.jpeg");
				img.scaleToFit(60, 60); // Or adjust as needed

				// Add the image to the second Paragraph (optional)
				imagePara.add(img); // Uncomment this line if you want a space below the heading before the image
				imagePara.setAlignment(Element.ALIGN_RIGHT); // Right-align the image

				// Add the Paragraphs to the document in the desired order
				document.add(imagePara);
				document.add(headerPara);
				document.add(Chunk.NEWLINE);


				float[] columnWidths = { 20f, 10f, 20f, 26};
				PdfPTable table = new PdfPTable(columnWidths);
				table.setWidthPercentage(100);
				
				PdfPCell headerCell = new PdfPCell();
				headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerCell.setBorderWidth(2);
				headerCell.setBorderColor(Color.red);
				headerCell.setFixedHeight(30);
				
				Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
				headFont.setColor(Color.BLUE);
				headFont.setStyle(Font.UNDERLINE);
				Stream.of("Name", "Age", "Mob Number", "Email").forEach(headerCellTitle -> {
//                  headerCell.setBackgroundColor(Color.CYAN);
					headerCell.setPhrase(new Phrase(headerCellTitle, headFont)); // setting the content of the headerCell
					table.addCell(headerCell);
				});
				for (Student student : allStudents) {
					PdfPCell cell = new PdfPCell();
//					cell.setPaddingLeft(4);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setFixedHeight(20);
					cell.setPhrase(new Phrase(student.getName()));
					table.addCell(cell);

					cell.setPhrase(new Phrase(String.valueOf(student.getAge())));
					table.addCell(cell);

			        cell.setPhrase(new Phrase(student.getPhone_number()));
					table.addCell(cell);

				    cell.setPhrase(new Phrase(student.getEmail_id()));
					table.addCell(cell);
				}
				document.add(table);
				document.close();

			} catch (DocumentException | IOException e) {
				logger.warn(e.getMessage());
			}
		} else {
			logger.warn("Student list is empty");
			throw new EmptyStudentListException("Student list is empty");
		}

	}

}
