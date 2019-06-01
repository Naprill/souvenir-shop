package com.summercamp.souvenirshop.repository;

import com.summercamp.souvenirshop.model.Purchase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

	Iterable<Purchase> findAllByOrderByDate();

	void deleteByDate(LocalDate date);

	@Query("select p from Purchase p where YEAR(p.date) = :year")
	List<Purchase> getAllPurchasesByYear(@Param("year") Integer year);

	@Modifying
	@Query("delete from Purchase p where YEAR(p.date) = :year")
	void deleteByYear(@Param("year") Integer year);
}
