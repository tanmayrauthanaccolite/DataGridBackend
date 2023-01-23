package com.example.DataTable.PdfExport;

import com.example.DataTable.Data.Salaries;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPDFExporter {
    private List<Salaries> listUsers;

    public UserPDFExporter(List<Salaries> listUsers) {
        this.listUsers = listUsers;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(7);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Discipline", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Rank", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Years Since Phd", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Yrs Experience", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sex", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Salary", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Salaries s : listUsers) {
            table.addCell(String.valueOf(s.getId()));
            table.addCell(s.getDiscipline());
            table.addCell(s.getRanks());
            table.addCell(String.valueOf(s.getYrSincePhd()));
            table.addCell(String.valueOf(s.getYrsService()));
            table.addCell(s.getSex());
            table.addCell(String.valueOf(s.getSalary()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 4.0f, 4.0f, 2.5f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}

