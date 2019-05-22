package com.summercamp.souvenirshop.controller;

import com.summercamp.souvenirshop.model.Purchase;
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
@RequestMapping("/purchase")
public class PurchaseController {

	private PurchaseService purchaseService;

	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}

	@ModelAttribute
	public Purchase purchase() {
		Purchase p = new Purchase();
		p.setDate(LocalDate.now());
		return p;
	}

	@GetMapping
	public String getPurchasePage() {
		return "purchase";
	}

	@PostMapping
	public String savePurchase(@ModelAttribute @Valid Purchase purchase,
	                           BindingResult result) {
		if (result.hasErrors()) {
			return "purchase";
		} else {
			purchaseService.create(purchase);
			return "purchase";
		}
	}


}
