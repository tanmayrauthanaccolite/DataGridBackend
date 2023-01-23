package com.example.DataTable.Repository;

import com.example.DataTable.Data.FilterBody;
import com.example.DataTable.Data.Salaries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salaries, Long> {
    @Query(value ="SELECT s from Salaries s where s.salary>:salmin and s.salary<:salmax" +
            " and s.sex in (:sex) and s.discipline in (:discipline) and s.ranks in (:rank)")
    Page<Salaries> findByFilter(Pageable pageable, @Param("salmin") long salmin, @Param("salmax") long salmax,
                                @Param("sex") List<String> sex,
                                @Param("discipline") List<String> discipline,
                                @Param("rank") List<String> rank);

    @Query(value ="SELECT s from Salaries s where s.salary>:salmin and s.salary<:salmax" +
            " and s.sex in (:sex) and s.discipline in (:discipline) and s.ranks in (:rank)")
    List<Salaries> getAllFilteredData(@Param("salmin") long salmin, @Param("salmax") long salmax,
                                @Param("sex") List<String> sex,
                                @Param("discipline") List<String> discipline,
                                @Param("rank") List<String> rank);
}
