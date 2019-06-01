package com.summercamp.souvenirshop.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
public class ClearPurchaseDTO {

	@PastOrPresent(message = "Choose date from past or present")
	@NotNull(message = "Date is necessary")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;

	/**
	 * if true - clear purchases for a whole year
	 */
	private Boolean yearFlag;
}
