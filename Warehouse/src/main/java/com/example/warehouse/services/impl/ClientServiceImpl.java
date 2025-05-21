package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.dto.AddressDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntitySummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityWithHistoryDto;
import com.example.warehouse.mappers.BusinessEntitySummaryMapper;
import com.example.warehouse.mappers.BusinesEntityWithHistoryMapper;
import com.example.warehouse.repositories.AddressRepository;
import com.example.warehouse.repositories.CityRepository;
import com.example.warehouse.repositories.ClientRepository;
import com.example.warehouse.repositories.CountryRepository;
import com.example.warehouse.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final BusinessEntitySummaryMapper businessEntitySummaryMapper;
    private final BusinesEntityWithHistoryMapper businessEntityWithHistoryMapper;

    public ClientServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, AddressRepository addressRepository, ClientRepository clientRepository, BusinessEntitySummaryMapper businessEntitySummaryMapper, BusinesEntityWithHistoryMapper businessEntityWithHistoryMapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
        this.businessEntitySummaryMapper = businessEntitySummaryMapper;
        this.businessEntityWithHistoryMapper = businessEntityWithHistoryMapper;
    }

    @Override
    public Client createClient(BusinessEntityDto request) {
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
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(address);

        return clientRepository.save(client);

    }

    @Override
    public List<BusinessEntitySummaryDto> getClientsWithTransactionCount(){
        return clientRepository.findAllClientsWithTransactionCounts()
                .stream()
                .map(businessEntitySummaryMapper::mapToClientDto)
                .toList();
    }

    @Override
    public BusinessEntityWithHistoryDto getClientWithHistory(Integer clientId) {
        Client client = clientRepository.findClientWithHistoryById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
        return businessEntityWithHistoryMapper.mapToDto(client);
    }
}
