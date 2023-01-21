package com.example.DataTable.Data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name="salaries")
public class Salaries {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "ranks")
    private String ranks;

    @Column(name = "discipline")
    private String discipline;

    @Column(name = "yrsSincePhd")
    private long yrSincePhd;

    @Column(name = "yrsService")
    private long yrsService;

    @Column(name = "sex")
    private String sex;

    @Column(name = "salary")
    private long salary;

    public  Salaries(){super();}
    public Salaries(long id, String rank, String discipline, long yrSincePhd, long yrsService, String sex, long salary) {
        this.id = id;
        this.ranks = rank;
        this.discipline = discipline;
        this.yrSincePhd = yrSincePhd;
        this.yrsService = yrsService;
        this.sex = sex;
        this.salary = salary;
    }
}
