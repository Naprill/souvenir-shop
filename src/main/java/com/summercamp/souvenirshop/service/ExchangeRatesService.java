package com.summercamp.souvenirshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.summercamp.souvenirshop.model.ExchangeRates;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRatesService {

	private static final String API_KEY = "63dad80abf04b6eaabbdbea71522a9ee";
	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;


	public ExchangeRatesService(RestTemplate restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	/**
	 * Call fixer.io Latest Rates Endpoint
	 * Base currency is always EUR - the only available option for free plan
	 *
	 * @param symbols - a list of comma-separated currency codes to limit output currencies
	 * @return map with exchange rates
	 */
	public Map<String, Float> getExchangeRates(String symbols) throws Exception {

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
