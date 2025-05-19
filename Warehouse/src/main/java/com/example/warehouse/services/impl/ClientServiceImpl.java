package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.dto.AddressDto;
import com.example.warehouse.domain.dto.ClientDto;
import com.example.warehouse.repositories.*;
import com.example.warehouse.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;


    public ClientServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, AddressRepository addressRepository, ClientRepository clientRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(ClientDto request) {
        AddressDto addressDto = request.getAddress();
        Optional<City> existingCity = cityRepository.findByPostalCodeAndNameAndCountry_Id(
                addressDto.getPostalCode(), addressDto.getCity(), addressDto.getCountryId()
        );
        City city;
        if (existingCity.isEmpty()){
            Country country = countryRepository.findById(addressDto.getCountryId())
                    .orElseThrow(() -> new NoSuchElementException("Country not found"));

            city = new City();
            city.setCountry(country);
            city.setName(addressDto.getCity());
            city.setPostalCode(addressDto.getPostalCode());
            cityRepository.save(city);
        } else {
            city = existingCity.get();
        }

        Address address = new Address();
        address.setCity(city);
        address.setStreet(addressDto.getStreet());
        address.setStreetNumber(addressDto.getStreetNumber());
        address = addressRepository.save(address);

        Client client = new Client();
        String name = request.getName();
        client.setName(name != null && !name.trim().isEmpty() ? name : null);
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(address);

        return clientRepository.save(client);

    }
}
