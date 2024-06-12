package rp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

@Controller
public class AdminReportListController {

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

	// レポート検索を処理するメソッド
	@PostMapping("/search_reports")
	public String serchReports(@RequestParam("search") String search, Model model) {
		// キーワードでレポートを検索
		List<Reports> reportList = reportsService.searchReportsByContent(search);
		// 検索結果をモデルに追加
		model.addAttribute("reportList", reportList);
		// レポート一覧画面のテンプレートを返す
		return "admin/report_list";
	}

	// レポートを削除するメソッド
	@PostMapping("/delete_repotrt")
	public String deleteReport(@RequestParam("reportId") Long reportId) {
		// 指定されたIDのレポートを削除する
		reportsService.deleteReport(reportId);
		// レポート一覧画面にリダイレクトする
		return "redirect:admin/report_list";
	}
}
