package com.example.warehouse;

import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Region;
import com.example.warehouse.domain.dto.AddressDto;
import com.example.warehouse.domain.dto.ClientDto;

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

    public static ClientDto createClientDto1(AddressDto addressDto){
        ClientDto clientDto = new ClientDto();
        clientDto.setAddress(addressDto);
        clientDto.setName("ExampleName");
        clientDto.setPhoneNumber("660 222 125");
        clientDto.setEmail("example@gmail.com");
        return clientDto;
    }

    public static AddressDto createAddressDto1(Country country){
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Pruszków");
        addressDto.setCountryId(country.getId());
        addressDto.setPostalCode("06-900");
        addressDto.setRegionId(country.getRegion().getId());
        addressDto.setStreet("Marynarska");
        addressDto.setStreetNumber(6);
        return  addressDto;
    }
}
