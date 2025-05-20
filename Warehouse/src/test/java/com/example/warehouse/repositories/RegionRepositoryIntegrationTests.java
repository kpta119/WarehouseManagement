package com.example.warehouse.repositories;

import com.example.warehouse.TestDataUtil;
import com.example.warehouse.domain.Region;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RegionRepositoryIntegrationTests {

    @Autowired
    private RegionRepository underTest;

    @Test
    public void testThatRegionCanBeCreatedAndRecalled(){
        Region region = TestDataUtil.createRegion1();
        underTest.save(region);
        Optional<Region> result = underTest.findById(region.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(region);
    }

    @Test
    public void testThatMultipleRegionsCanBeCreatedAndRecalled() {
        Region region = TestDataUtil.createRegion1();
        underTest.save(region);
        Region region2 = TestDataUtil.createRegion2();
        underTest.save(region2);
        Region region3 = TestDataUtil.createRegion3();
        underTest.save(region3);

        Iterable<Region> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(region, region2, region3);
    }

    @Test
    public void testThatRegionCanBeUpdated(){
        Region region = TestDataUtil.createRegion1();
        underTest.save(region);
        region.setName("Updated");
        underTest.save(region);
        Optional<Region> result = underTest.findById(region.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(region);
    }

    @Test
    public void testThatRegionCanBeDeleted(){
        Region region = TestDataUtil.createRegion1();
        underTest.save(region);
        Optional<Region> result = underTest.findById(region.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(region);

        underTest.deleteById(region.getId());
        Optional<Region> resultAfterDeleting = underTest.findById(region.getId());
        assertThat(resultAfterDeleting).isEmpty();
    }
}

