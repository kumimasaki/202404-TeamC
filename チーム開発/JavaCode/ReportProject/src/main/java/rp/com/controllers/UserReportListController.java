package rp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

@Controller
@RequestMapping("/user/reports")
public class UserReportListController {

	@Autowired
	private ReportsService reportsService;

	// レポート一覧画面を表示するメソッド
	@GetMapping("/user/report/view-list")
	public String showReportList(Model model) {
		// すべてのレポートを取得してモデルに追加
		model.addAttribute("reports", reportsService.getAllReports());
		// レポート一覧画面のテンプレートを返す
		return "user_reports.html";
	}

	// レポートを削除するメソッド
	@PostMapping("/user/delete_repotrt")
	public String deleteReport(@RequestParam("reportId") Long reportId) {
		// 指定されたIDのレポートを削除する
		reportsService.deleteReport(reportId);
		// レポート一覧画面にリダイレクトする
		return "redirect:user/report_list";
	}

	@GetMapping
	public String listReports(Model model) {
		List<Reports> reports = reportsService.getAllReports();
		model.addAttribute("reports", reports);
		return "user_reports";
	}

	@PostMapping("/search")
	public String searchReports(@RequestParam("keyword") String keyword, Model model) {
		List<Reports> reports = reportsService.searchReportsByKeyword(keyword);
		model.addAttribute("reports", reports);
		return "user_reports";
	}

}
