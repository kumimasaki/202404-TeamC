package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

import java.util.Optional;

@Controller
@RequestMapping("/user/report/edit")
public class UserReportEditController {

	@Autowired
	private ReportsService reportsService;

	// 報告修正ページを表示するメソッド
	@GetMapping("/{reportId}")
	public String showEditReportPage(@PathVariable Long reportId, Model model) {
		Optional<Reports> reportOptional = reportsService.getReportById(reportId);
		if (reportOptional.isPresent()) {
			Reports report = reportOptional.get();
			model.addAttribute("report", report);
			return "user/edit_report";
		} else {
			return "redirect:/user/report/list";
		}
	}
}
