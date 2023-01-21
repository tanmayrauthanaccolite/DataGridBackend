package com.example.DataTable.Service;

import com.example.DataTable.Data.Salaries;
import com.example.DataTable.Repository.SalaryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salaries> saveData()
    {
        File file= new File("C:\\Users\\tanmay.rauthan\\Documents\\DataTable\\DataTable\\src\\main\\resources\\static\\Salaries.csv");
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Salaries> salaries = new ArrayList<Salaries>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            int id=1;
            for (CSVRecord csvRecord : csvRecords) {
                Salaries salary = new Salaries(id++,
                        csvRecord.get("rank"),
                        csvRecord.get("discipline"),
                        Long.parseLong(csvRecord.get("yrs.since.phd")),
                        Long.parseLong(csvRecord.get("yrs.service")),
                        csvRecord.get("sex"),
                        Long.parseLong(csvRecord.get("salary"))
                );
                salaries.add(salary);
            }
            salaryRepository.saveAll(salaries);
            //System.out.println(salaries.get(0).getId());
            return salaries;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public List<Salaries> getData()
    {
        return salaryRepository.findAll();
    }
}
