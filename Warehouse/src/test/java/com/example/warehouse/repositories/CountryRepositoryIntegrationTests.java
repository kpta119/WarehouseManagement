package com.example.warehouse.repositories;

import com.example.warehouse.TestDataUtil;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Region;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CountryRepositoryIntegrationTests {

    @Autowired
    private CountryRepository underTest;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void testThatCountryCanBeCreatedAndRecalled() {
        Region region = TestDataUtil.createRegion2();
        regionRepository.save(region);
        Country country = TestDataUtil.createCountry1(region);
        underTest.save(country);

        Optional<Country> result = underTest.findById(country.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(country);
        assertThat(result.get().getRegion()).isEqualTo(region);

        Optional<Region> resultRegion = regionRepository.findById(region.getId());
        assertThat(resultRegion).isPresent();
        assertThat(resultRegion.get()).isEqualTo(region);
    }

    @Test
    public void testThatMultipleCountriesCanBeCreated() {
        Region region1 = TestDataUtil.createRegion1();
        regionRepository.save(region1);
        Country country1 = TestDataUtil.createCountry1(region1);
        underTest.save(country1);
        Region region2 = TestDataUtil.createRegion2();
        regionRepository.save(region2);
        Country country2 = TestDataUtil.createCountry2(region2);
        underTest.save(country2);
        Region region3 = TestDataUtil.createRegion3();
        regionRepository.save(region3);
        Country country3 = TestDataUtil.createCountry3(region3);
        underTest.save(country3);

        Iterable<Country> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(country1, country2, country3);
    }

    @Test
    public void testThatCountriesCanBeFoundFromGivenRegion() {
        Region region = TestDataUtil.createRegion1();
        regionRepository.save(region);
        Country country1 = TestDataUtil.createCountry1(region);
        underTest.save(country1);
        Region anotherRegion = TestDataUtil.createRegion2();
        regionRepository.save(anotherRegion);
        Country country2 = TestDataUtil.createCountry2(anotherRegion);
        underTest.save(country2);
        Country country3 = TestDataUtil.createCountry3(region);
        underTest.save(country3);

        Iterable<Country> foundCountries = underTest.findByRegionId(region.getId());
        assertThat(foundCountries).hasSize(2).containsExactly(country1, country3);
    }
}

