package com.example.DataTable.Data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterBody {
    List<String> disciplines;
    List<String> ranks;
    long salarymin;
    long salarymax;
    List<String> sex;
}
