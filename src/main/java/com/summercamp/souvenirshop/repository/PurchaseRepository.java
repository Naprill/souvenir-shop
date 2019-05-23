package com.summercamp.souvenirshop.repository;

import com.summercamp.souvenirshop.model.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

	Iterable<Purchase> findAllByOrderByDate();
}
