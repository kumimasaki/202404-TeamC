package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rp.com.services.ReportsService;

@Controller
@RequestMapping("/user/report/hide")
public class UserReportHideController {

	@Autowired
	private ReportsService reportsService;

	// 報告非表示処理を行うメソッド
	@PostMapping("/{reportId}")
	public String hideReport(@PathVariable Long reportId) {
		reportsService.hideReportById(reportId);
		return "redirect:/user/reports";
	}
}
