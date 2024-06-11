package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

import java.util.List;

@Controller
@RequestMapping("/user/report/search")
public class UserReportSearchController {

	@Autowired
	private ReportsService reportsService;

	// 報告検索機能を行うメソッド
	@GetMapping
	public String searchReports(@RequestParam("query") String query, Model model) {
		List<Reports> reports = reportsService.searchReportsByTitleOrContent(query);
		model.addAttribute("reports", reports);
		return "user/report_list";
	}
}
