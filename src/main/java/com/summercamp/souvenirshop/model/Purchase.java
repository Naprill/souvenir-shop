package com.summercamp.souvenirshop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotBlank(message = "Name is necessary")
	@Column(nullable = false)
	private String name;

	@PastOrPresent(message = "Choose date from past or present")
	@NotNull(message = "Date is necessary")
	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;

	@Positive(message = "Price should be positive")
	@NotNull(message = "Price is necessary")
	@Column(nullable = false)
	private Float price;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Currency currency;
}
