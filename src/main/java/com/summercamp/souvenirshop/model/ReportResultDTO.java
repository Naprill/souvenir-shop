package com.summercamp.souvenirshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
public class ReportResultDTO {

	@Enumerated(EnumType.STRING)
	private Currency currency;

	private Float amount;
}
