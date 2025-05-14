package com.example.warehouse.repositories;

import com.example.warehouse.TestDataUtil;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CountryRepositoryIntegrationTests {

    @Autowired
    private CountryRepository underTest;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @Transactional
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
}

