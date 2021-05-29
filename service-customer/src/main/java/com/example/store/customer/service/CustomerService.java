package com.example.store.customer.service;

import java.util.List;

import com.example.store.customer.model.Customer;
import com.example.store.customer.model.Region;

public interface CustomerService {

	
    public List<Customer> findCustomerAll();
    public List<Customer> findCustomersByRegion(Region region);

    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Customer customer);
    public  Customer getCustomer(Long id);
}
