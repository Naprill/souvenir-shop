package com.summercamp.souvenirshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReportDTO {

	@NotNull(message = "Year is necessary")
	private Integer year;

	@Enumerated(EnumType.STRING)
	private Currency currency;
}
