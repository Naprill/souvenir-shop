package com.summercamp.souvenirshop;

import com.summercamp.souvenirshop.model.Currency;
import com.summercamp.souvenirshop.model.Purchase;
import com.summercamp.souvenirshop.repository.PurchaseRepository;
import com.summercamp.souvenirshop.service.PurchaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class PurchaseServiceTest {

	@InjectMocks
	private PurchaseService purchaseService;

	@Mock
	private PurchaseRepository repository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createPurchaseTest() {
		Purchase entity = new Purchase();
		entity.setPrice(5f);
		entity.setCurrency(Currency.USD);
		Mockito.when(repository.save(entity)).thenReturn(entity);

		assertThat(purchaseService.create(entity), is(entity));
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPurchaseTest_throwsException(){
		Purchase entity = new Purchase();
		entity.setId(1L);
		entity.setPrice(5f);
		entity.setCurrency(Currency.USD);
		Mockito.when(repository.save(entity)).thenReturn(entity);

		purchaseService.create(entity);
	}
}
