package com.summercamp.souvenirshop.service;

import com.summercamp.souvenirshop.model.Purchase;
import com.summercamp.souvenirshop.model.ReportDTO;
import com.summercamp.souvenirshop.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

	private PurchaseRepository repository;
	private ExchangeRatesService exchangeRatesService;

	public ReportService(PurchaseRepository repository, ExchangeRatesService exchangeRatesService) {
		this.repository = repository;
		this.exchangeRatesService = exchangeRatesService;
	}

	public String processReportRequest(ReportDTO dto) throws Exception {

		List<Purchase> list = repository.getAllPurchasesByYear(dto.getYear());
		if (list.isEmpty()) {
			return "There are no purchases for this year";
		}

		String currencies = list.stream()
				.map(Purchase::getCurrency)
				.map(Enum::toString)
				.collect(Collectors.joining(","));

		Map<String, Float> exchangeRates = exchangeRatesService.getExchangeRates(currencies);

		Float amountInEuro = 0f;
		for (Purchase purchase : list) {
			amountInEuro += purchase.getPrice() / exchangeRates.get(purchase.getCurrency().toString());
		}

		String targetCurrency = dto.getCurrency().toString();
		Map<String, Float> exchangeRate = exchangeRatesService.getExchangeRates(targetCurrency);
		Float amountInTargetCurrency = amountInEuro * exchangeRate.get(targetCurrency);

		return amountInTargetCurrency + " " + targetCurrency;
	}

}
