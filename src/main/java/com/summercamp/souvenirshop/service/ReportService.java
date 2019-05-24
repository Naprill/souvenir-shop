package com.summercamp.souvenirshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.summercamp.souvenirshop.model.ExchangeRates;
import com.summercamp.souvenirshop.model.Purchase;
import com.summercamp.souvenirshop.model.ReportDTO;
import com.summercamp.souvenirshop.repository.PurchaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

	private static final String API_KEY = "63dad80abf04b6eaabbdbea71522a9ee";
	private PurchaseRepository repository;
	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;

	public ReportService(PurchaseRepository repository,
	                     RestTemplate restTemplate,
	                     ObjectMapper objectMapper) {
		this.repository = repository;
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
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

		Map<String, Float> exchangeRates = getExchangeRates(currencies);

		Float amountInEuro = 0f;
		for (Purchase purchase : list) {
			for (String currency : exchangeRates.keySet()) {
				if (currency.equals(purchase.getCurrency().toString())) {
					amountInEuro += purchase.getPrice() / exchangeRates.get(currency);
				}
			}
		}

		String targetCurrency = dto.getCurrency().toString();
		Map<String, Float> exchangeRate = getExchangeRates(targetCurrency);
		Float amountInTargetCurrency = amountInEuro * exchangeRate.get(targetCurrency);

		return "result: " + amountInTargetCurrency + " " + targetCurrency;
	}

	/**
	 * Call fixer.io Latest Rates Endpoint
	 * Base currency is always EUR - the only available option for free plan
	 *
	 * @param symbols - a list of comma-separated currency codes to limit output currencies
	 * @return map with exchange rates
	 */
	private Map<String, Float> getExchangeRates(String symbols) throws Exception {

		ResponseEntity<String> response
				= restTemplate.getForEntity("http://data.fixer.io/api/latest" +
				"?access_key=" + API_KEY +
				"&base=EUR" +
				"&symbols=" + symbols, String.class);
		ExchangeRates exchangeRates = objectMapper.readValue(response.getBody(), ExchangeRates.class);
		if (Boolean.parseBoolean(exchangeRates.getSuccess())) {
			return exchangeRates.getRates();
		} else {
			throw new Exception();
		}
	}

}
