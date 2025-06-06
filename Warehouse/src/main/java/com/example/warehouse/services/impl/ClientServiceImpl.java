package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;
import com.example.warehouse.domain.dto.filtersDto.ClientSearchFilters;
import com.example.warehouse.mappers.BusinesEntityWithHistoryMapper;
import com.example.warehouse.mappers.BusinessEntitySummaryMapper;
import com.example.warehouse.repositories.AddressRepository;
import com.example.warehouse.repositories.CityRepository;
import com.example.warehouse.repositories.ClientRepository;
import com.example.warehouse.repositories.CountryRepository;
import com.example.warehouse.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Address addressSaved = addressRepository.save(address);

        Client client = new Client();
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(addressSaved);

        return clientRepository.save(client);

    }

    @Override
    public Page<ClientSummaryDto> getClientsWithTransactionCount(ClientSearchFilters filters, Pageable pageable){
        Page<Object[]> results = clientRepository.findAllClientsWithTransactionCounts(filters, pageable);
        return results.map(businessEntitySummaryMapper::mapToClientDto);
    }

    @Override
    public ClientWithHistoryDto getClientWithHistory(Integer clientId) {
        Client client = clientRepository.findClientWithHistoryById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
        return businessEntityWithHistoryMapper.mapToDto(client);
    }
}
