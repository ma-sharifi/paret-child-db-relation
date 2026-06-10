package com.example.parentchilddbrelation.repository;

import com.example.parentchilddbrelation.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
}
