package com.example.warehouse;

import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Region;

public class TestDataUtil {

    private TestDataUtil(){}

    public static Region createRegion1(){
        Region region = new Region();
        region.setName("Asia");
        return region;
    }

    public static Region createRegion2(){
        Region region = new Region();
        region.setName("Europe");
        return region;
    }

    public static Region createRegion3(){
        Region region = new Region();
        region.setName("Australia");
        return region;
    }

    public static Country createCountry1(Region region){
        Country country = new Country();
        country.setName("Poland");
        country.setRegion(region);
        country.setCountryCode("PL");
        return country;
    }

    public static Country createCountry2(Region region){
        Country country = new Country();
        country.setName("China");
        country.setRegion(region);
        country.setCountryCode("CHN");
        return country;
    }

    public static Country createCountry3(Region region){
        Country country = new Country();
        country.setName("Germany");
        country.setRegion(region);
        country.setCountryCode("GER");
        return country;
    }
}
