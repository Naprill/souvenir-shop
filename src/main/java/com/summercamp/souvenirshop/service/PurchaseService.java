package com.summercamp.souvenirshop.service;

import com.summercamp.souvenirshop.model.Purchase;
import com.summercamp.souvenirshop.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class PurchaseService {

	private PurchaseRepository repository;

	public PurchaseService(PurchaseRepository purchaseRepository) {
		this.repository = purchaseRepository;
	}

	public Purchase create(Purchase entity) {
		if (!isNull(entity.getId())) {
			throw new IllegalArgumentException("Could not create entity. Entity has already exists");
		}
		return repository.save(entity);
	}

	public Iterable<Purchase> getAllOrderedByDate() {
		return repository.findAllByOrderByDate();
	}
}
