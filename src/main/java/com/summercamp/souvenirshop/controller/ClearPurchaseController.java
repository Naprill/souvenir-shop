package com.summercamp.souvenirshop.controller;

import com.summercamp.souvenirshop.model.ClearPurchaseDTO;
import com.summercamp.souvenirshop.service.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/clear")
public class ClearPurchaseController {

	private PurchaseService service;

	public ClearPurchaseController(PurchaseService service) {
		this.service = service;
	}

	@ModelAttribute("dto")
	public ClearPurchaseDTO clearPurchaseDTO() {
		ClearPurchaseDTO dto = new ClearPurchaseDTO();
		dto.setDate(LocalDate.now());
		return dto;
	}

	@GetMapping()
	public String getClearPurchasePage() {
		return "clear";
	}

	@PostMapping()
	public String clearPurchase(@ModelAttribute @Valid ClearPurchaseDTO dto,
	                            BindingResult result) {
		if (result.hasErrors()) {
			return "clear";
		} else {
			service.clear(dto);
			return "redirect:purchase/all";
		}
	}

}
