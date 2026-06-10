package com.example.paretchilddbrelation.repository;

import com.example.paretchilddbrelation.entity.Child1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Child1Repository extends JpaRepository<Child1, Long> {
}
