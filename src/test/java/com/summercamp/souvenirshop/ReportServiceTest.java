package com.summercamp.souvenirshop;

import com.summercamp.souvenirshop.model.Currency;
import com.summercamp.souvenirshop.model.Purchase;
import com.summercamp.souvenirshop.model.ReportDTO;
import com.summercamp.souvenirshop.model.ReportResultDTO;
import com.summercamp.souvenirshop.repository.PurchaseRepository;
import com.summercamp.souvenirshop.service.ExchangeRatesService;
import com.summercamp.souvenirshop.service.ReportService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class ReportServiceTest {

	@InjectMocks
	private ReportService reportService;

	@Mock
	private PurchaseRepository repository;

	@Mock
	private ExchangeRatesService exchangeRatesService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processReportRequestTest() throws Exception {
		ReportDTO dto = new ReportDTO();
		dto.setYear(2019);
		dto.setCurrency(Currency.EUR);
		Purchase purchase = new Purchase();
		purchase.setCurrency(Currency.UAH);
		purchase.setPrice(90f);
		List<Purchase> list = Lists.list(purchase);
		Mockito.when(repository.getAllPurchasesByYear(dto.getYear())).thenReturn(list);

		String currencies = purchase.getCurrency().toString();
		Map<String, Float> exchangeRates = new HashMap<>();
		exchangeRates.put(currencies, 30f);
		Mockito.when(exchangeRatesService.getExchangeRates(currencies)).thenReturn(exchangeRates);

		String targetCurrency = dto.getCurrency().toString();
		Map<String, Float> exchangeRate = new HashMap<>();
		exchangeRate.put(targetCurrency, 1f);
		Mockito.when(exchangeRatesService.getExchangeRates(targetCurrency)).thenReturn(exchangeRate);

		ReportResultDTO result = new ReportResultDTO(Currency.EUR, 3f);
		assertThat(reportService.processReportRequest(dto), is(result));

	}

	@Test
	public void givenEmptyList_WhenReportRequest_ThenReturnEmptyDTO()throws Exception{
		ReportDTO dto = new ReportDTO();
		dto.setYear(2019);
		dto.setCurrency(Currency.EUR);

		List<Purchase> list = Lists.emptyList();
		Mockito.when(repository.getAllPurchasesByYear(dto.getYear())).thenReturn(list);

		ReportResultDTO result = new ReportResultDTO(null, 0f);
		assertEquals(reportService.processReportRequest(dto), result);
	}

	@Test
	public void processReportRequestTest2() throws Exception {
		ReportDTO dto = new ReportDTO();
		dto.setYear(2019);
		dto.setCurrency(Currency.UAH);
		Purchase purchase = new Purchase();
		purchase.setCurrency(Currency.UAH);
		purchase.setPrice(99f);
		Purchase purchase1 = new Purchase();
		purchase1.setCurrency(Currency.UAH);
		purchase1.setPrice(1f);
		List<Purchase> list = Lists.list(purchase, purchase1);
		Mockito.when(repository.getAllPurchasesByYear(dto.getYear())).thenReturn(list);

		String currencies = purchase.getCurrency().toString();
		Map<String, Float> exchangeRates = new HashMap<>();
		exchangeRates.put(currencies, 30f);
		Mockito.when(exchangeRatesService.getExchangeRates(currencies)).thenReturn(exchangeRates);

		String targetCurrency = dto.getCurrency().toString();
		Map<String, Float> exchangeRate = new HashMap<>();
		exchangeRate.put(targetCurrency, 30f);
		Mockito.when(exchangeRatesService.getExchangeRates(targetCurrency)).thenReturn(exchangeRate);

		ReportResultDTO result = new ReportResultDTO(Currency.UAH, 100f);
		assertThat(reportService.processReportRequest(dto), is(result));
	}
}
