package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Province;

import java.util.List;

public interface CustomerService {
    Iterable<Customer> findAll();

    void save(Customer customer);

    Customer findById(Long id);

    void remove(Long id);

    Iterable<Customer> findAllByProvince(Province province);
}
