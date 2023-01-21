package com.example.DataTable.Controller;

import com.example.DataTable.Data.Salaries;
import com.example.DataTable.Service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/getData")
    public ResponseEntity<List<Salaries>> getData()
    {
        return new ResponseEntity<List<Salaries>>(salaryService.getData(), HttpStatus.OK);
    }
}
