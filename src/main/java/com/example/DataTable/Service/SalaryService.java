package com.example.DataTable.Service;

import com.example.DataTable.Data.FilterBody;
import com.example.DataTable.Data.Salaries;
import com.example.DataTable.Repository.SalaryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

@Service
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salaries> saveData()
    {
        File file= new File("C:\\Users\\tanmay.rauthan\\Documents\\DataTable\\DataTable\\src\\main\\resources\\static\\salaries_new.csv");
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
    public Page<Salaries> getData(int page, int size, FilterBody filterBody)
    {
        System.out.println(filterBody.getSex().isEmpty());
        Pageable paging = PageRequest.of(page, size);
        if(filterBody.getRanks().isEmpty()){
            List<String> defaultRanks=new ArrayList<String>(){{
                add("Prof");
                add("AssocProf");
                add("AsstProf");
            }};
            filterBody.setRanks(defaultRanks);
        }
        if(filterBody.getSex().isEmpty())
        {
            System.out.println("hi");
            List<String> sex=new ArrayList<String>(){{
                add("Male");
                add("Female");
            }};
            filterBody.setSex(sex);
        }
        if(filterBody.getDisciplines().isEmpty())
        {
            List<String> disciplines=new ArrayList<String>(){{
                add("A");
                add("B");
            }};
            filterBody.setDisciplines(disciplines);
        }
        if(filterBody.getSalarymin()==-1)
            filterBody.setSalarymin(0);
        if(filterBody.getSalarymax()==-1)
            filterBody.setSalarymax(10000000);
        System.out.println(filterBody.getSex());
        return salaryRepository.findByFilter(paging,filterBody.getSalarymin(),filterBody.getSalarymax(),filterBody.getSex(),filterBody.getDisciplines(),filterBody.getRanks());
    }

    public List<Salaries> getPdfData(FilterBody filterBody)
    {
        System.out.println(filterBody.getSex().isEmpty());
        if(filterBody.getRanks().isEmpty()){
            List<String> defaultRanks=new ArrayList<String>(){{
                add("Prof");
                add("AssocProf");
                add("AsstProf");
            }};
            filterBody.setRanks(defaultRanks);
        }
        if(filterBody.getSex().isEmpty())
        {
            System.out.println("hi");
            List<String> sex=new ArrayList<String>(){{
                add("Male");
                add("Female");
            }};
            filterBody.setSex(sex);
        }
        if(filterBody.getDisciplines().isEmpty())
        {
            List<String> disciplines=new ArrayList<String>(){{
                add("A");
                add("B");
            }};
            filterBody.setDisciplines(disciplines);
        }
        if(filterBody.getSalarymin()==-1)
            filterBody.setSalarymin(0);
        if(filterBody.getSalarymax()==-1)
            filterBody.setSalarymax(10000000);
        System.out.println(filterBody.getSex());
        return salaryRepository.getAllFilteredData(filterBody.getSalarymin(),filterBody.getSalarymax(),filterBody.getSex(),filterBody.getDisciplines(),filterBody.getRanks());
    }

}
