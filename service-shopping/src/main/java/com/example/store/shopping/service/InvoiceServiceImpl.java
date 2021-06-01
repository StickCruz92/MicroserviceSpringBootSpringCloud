package com.example.store.shopping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store.shopping.client.CustomerClient;
import com.example.store.shopping.client.ProductClient;
import com.example.store.shopping.entity.Invoice;
import com.example.store.shopping.entity.InvoiceItem;
import com.example.store.shopping.model.Customer;
import com.example.store.shopping.model.Product;
import com.example.store.shopping.repository.InvoiceItemsRepository;
import com.example.store.shopping.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemsRepository invoiceItemsRepository;
    
    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;
    
    
    @Override
    public List<Invoice> findInvoiceAll() {
        return  invoiceRepository.findAll();
    }


    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);
        invoiceDB.getItems().forEach( invoiceItem -> {
            productClient.updateStockProduct( invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });
        return invoiceDB;
    }
    

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    
    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice= invoiceRepository.findById(id).orElse(null);
        if (invoice != null){
        	Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
        	invoice.setCustomer(customer);
        	List<InvoiceItem> listItem = invoice.getItems().stream().map( invoiceItem -> {
        		Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
        		invoiceItem.setProduct(product);
        		return invoiceItem;
        	}).collect(Collectors.toList());
        	invoice.setItems(listItem);
        }
        return invoice ;
    }
    
}
