package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportDetailsController {

	@Autowired
	private ReportsService reportsService;


	// IDでレポートを取得し
	@GetMapping("/{id}")
	public String getReportById(@PathVariable Long id, Model model) {
		Optional<Reports> report = reportsService.getReportById(id);
		if (report.isPresent()) {
			model.addAttribute("report", report.get());
			return "admin_report_details";
		} else {
			return "redirect:/admin/reports";
		}
	}

	// 新しいレポートを作成するフォームの表示
	@GetMapping("/new")
	public String showCreateReportForm(Model model) {
		model.addAttribute("report", new Reports());
		return "admin_report_create";
	}

	// 新しいレポートを作成し
	@PostMapping
	public String createReport(@ModelAttribute Reports report) {
		reportsService.createReport(report);
		return "redirect:/admin/reports";
	}

	// レポートを受領し
	@PostMapping("/{id}/receive")
	public String receiveReport(@PathVariable Long id) {
		reportsService.acceptReport(id);
		return "redirect:/admin/reports";
	}
}