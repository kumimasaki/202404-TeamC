package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import rp.com.services.ReportsService;

@Controller
public class ReportListController {

	@Autowired
	private ReportsService reportsService;

	// レポート一覧画面を表示するメソッド
	@GetMapping("/admin/report/list")
	public String showReportList(Model model) {
		// すべてのレポートを取得してモデルに追加
		model.addAttribute("reports", reportsService.getAllReports());
		// レポート一覧画面のテンプレートを返す
		return "admin/report_list";
	}
}
