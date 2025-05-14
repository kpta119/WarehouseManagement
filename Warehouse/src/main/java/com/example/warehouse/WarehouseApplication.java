package com.example.warehouse;

import com.example.warehouse.domain.Region;
import com.example.warehouse.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehouseApplication implements CommandLineRunner {

	@Autowired
	private RegionRepository regionRepository;

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Region region = new Region();
		region.setName("Europe");
		Region savedReg = regionRepository.save(region);
		System.out.println("Saved region is: Id= " + savedReg.getId() + "Name= " + savedReg.getName());
	}
}