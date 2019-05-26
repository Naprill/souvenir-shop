package com.summercamp.souvenirshop.controller;

import com.summercamp.souvenirshop.model.ReportDTO;
import com.summercamp.souvenirshop.model.ReportResultDTO;
import com.summercamp.souvenirshop.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/report")
public class ReportController {

	private ReportService service;

	public ReportController(ReportService service) {
		this.service = service;
	}

	@ModelAttribute("report")
	public ReportDTO reportDTO() {
		ReportDTO r = new ReportDTO();
		r.setYear(LocalDate.now().getYear());
		return r;
	}

	@GetMapping
	public String getReportPage() {
		return "report";
	}

	@PostMapping
	public ModelAndView sendReportRequest(@ModelAttribute("report") @Valid ReportDTO reportDTO,
	                                      BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("report");
		if (result.hasErrors()) {
			return modelAndView;
		} else {
			ReportResultDTO reportResult;
			try {
				reportResult = service.processReportRequest(reportDTO);
			} catch (Exception e) {
				modelAndView.addObject("result", "Something went wrong...");
				return modelAndView;
			}

			if (reportResult.getAmount().equals(0f)) {
				modelAndView.addObject("result", "There are no purchases for this year");
			} else {
				modelAndView.addObject("result",
						reportResult.getAmount() + " " + reportResult.getCurrency().toString());
			}
			return modelAndView;
		}
	}

}
