package rp.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rp.com.models.entity.Reports;

@Repository
public interface ReportsDao extends JpaRepository<Reports, Long> {

}
