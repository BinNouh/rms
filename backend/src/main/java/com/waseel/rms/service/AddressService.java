package com.waseel.rms.service;

import com.waseel.rms.entity.Address;
import com.waseel.rms.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address newAddress) {
        return addressRepository.findById(id)
                .map(address -> {
                    address.setEmailAddress(newAddress.getEmailAddress());
                    address.setMobileNumber(newAddress.getMobileNumber());
                    address.setCity(newAddress.getCity());
                    address.setZipCode(newAddress.getZipCode());
                    address.setAdditionalCode(newAddress.getAdditionalCode());
                    address.setCountry(newAddress.getCountry());
                    address.setAddressLine(newAddress.getAddressLine());
                    return addressRepository.save(address);
                })
                .orElseGet(() -> {
                    newAddress.setAddress_id(id);
                    return addressRepository.save(newAddress);
                });
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
