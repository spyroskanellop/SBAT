package com.example.springbootapplicationtask;

import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class SpringBootApplicationTaskApplication implements CommandLineRunner {
	@Autowired
	AreaRepository areaRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplicationTaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		areaRepository.save(new Area( "ΑΙΤΩΛΟΑΚΑΡΝΑΝΙΑΣ", 702, 1297,834,97,2036,new Date(),
//				34083,34083,11278,45170));
	}
}
