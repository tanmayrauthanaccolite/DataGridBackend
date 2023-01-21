package com.example.DataTable.Repository;

import com.example.DataTable.Data.Salaries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salaries, Long> {
}
