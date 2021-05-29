package com.example.store.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.store.shopping.entity.InvoiceItem;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {

}
