package com.example.warehouse;

import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Region;
import com.example.warehouse.domain.dto.addressDtos.AddressInfoDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static Region createRegion1() {
        Region region = new Region();
        region.setName("Asia");
        return region;
    }

    public static Region createRegion2() {
        Region region = new Region();
        region.setName("Europe");
        return region;
    }

    public static Region createRegion3() {
        Region region = new Region();
        region.setName("Australia");
        return region;
    }

    public static Country createCountry1(Region region) {
        Country country = new Country();
        country.setName("Poland");
        country.setRegion(region);
        country.setCountryCode("PL");
        return country;
    }

    public static Country createCountry2(Region region) {
        Country country = new Country();
        country.setName("China");
        country.setRegion(region);
        country.setCountryCode("CHN");
        return country;
    }

    public static Country createCountry3(Region region) {
        Country country = new Country();
        country.setName("Germany");
        country.setRegion(region);
        country.setCountryCode("GER");
        return country;
    }

    public static BusinessEntityDto createClientDto1(AddressInfoDto addressDto) {
        BusinessEntityDto businessEntityDto = new BusinessEntityDto();
        businessEntityDto.setAddress(addressDto);
        businessEntityDto.setName("ExampleName");
        businessEntityDto.setPhoneNumber("660 222 125");
        businessEntityDto.setEmail("example@gmail.com");
        return businessEntityDto;
    }

    public static AddressInfoDto createAddressDto1(Country country) {
        AddressInfoDto addressDto = new AddressInfoDto();
        addressDto.setCityName("Pruszków");
        addressDto.setCountryId(country.getId());
        addressDto.setPostalCode("06-900");
        addressDto.setStreet("Marynarska");
        addressDto.setStreetNumber(6);
        return addressDto;
    }
}
