package com.qsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qsp.entity.WeatherReport;


@Repository
public interface WeatherRepositoy extends JpaRepository<WeatherReport, Integer>{
	
}
