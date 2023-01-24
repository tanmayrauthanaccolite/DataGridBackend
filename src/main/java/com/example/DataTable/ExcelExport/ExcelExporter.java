package com.example.DataTable.ExcelExport;
import com.example.DataTable.Data.Salaries;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ExcelExporter {
    private List<Salaries> listItems=new ArrayList<>();
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelExporter(List<Salaries> listItems) {
        this.listItems=listItems;
        workbook = new XSSFWorkbook();
    }
    private void writeHeader() {
        sheet = workbook.createSheet("Items List");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Discipline", style);
        createCell(row, 2, "Rank", style);
        createCell(row, 3, "Yrs Since Phd", style);
        createCell(row, 4, "Yrs Exp", style);
        createCell(row, 5, "Sex", style);
        createCell(row, 6, "Salary", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Salaries record: listItems) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getDiscipline(), style);
            createCell(row, columnCount++, record.getRanks(), style);
            createCell(row, columnCount++, record.getYrSincePhd(), style);
            createCell(row, columnCount++, record.getYrsService(), style);
            createCell(row, columnCount++, record.getSex(), style);
            createCell(row, columnCount++, record.getSalary(), style);
        }
    }
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
