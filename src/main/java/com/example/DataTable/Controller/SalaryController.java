package com.example.DataTable.Controller;

import com.example.DataTable.Data.FilterBody;
import com.example.DataTable.Data.Salaries;
import com.example.DataTable.ExcelExport.ExcelExporter;
import com.example.DataTable.PdfExport.UserPDFExporter;
import com.example.DataTable.Service.SalaryService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/putData")
    public ResponseEntity<List<Salaries>> putData()
    {
        return new ResponseEntity<List<Salaries>>(salaryService.saveData(), HttpStatus.OK);
    }
    @PostMapping("/getData")
    public ResponseEntity<Page<Salaries>> getData(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestBody FilterBody filterBody)
    {
        System.out.println(filterBody.getSex());
        return new ResponseEntity<Page<Salaries>>(salaryService.getData(page,size,filterBody), HttpStatus.OK);
    }

    @PostMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response,@RequestBody FilterBody filterBody) throws DocumentException, IOException {
        System.out.println("yoyo");
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Salaries> listUsers = salaryService.getPdfData(filterBody);
        System.out.println("Size is"+listUsers.size());
        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);
    }
    @PostMapping("/exportExcel")
    public void exportIntoExcelFile(HttpServletResponse response, @RequestBody FilterBody filterBody) throws IOException {
        List<Salaries> excelList=salaryService.getPdfData(filterBody);
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ListItems" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExcelExporter generator = new ExcelExporter(excelList);
        generator.generateExcelFile(response);
    }
}
