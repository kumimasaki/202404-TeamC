package rp.com.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

@Controller
@RequestMapping("/reports")
public class ReportDetailsController {
	@Autowired
	private ReportsService reportService;

	// すべてのレポートを取得し、@return レポートリストのビュー
	@GetMapping
	public String getAllReports(Model model) {
		List<Reports> reports = reportService.getAllReports();
		model.addAttribute("reports", reports);
		return "reports/list"; // 返回视图名称，例如 "reports/list"
	}

	// IDでレポートを取得し、@param id レポートID、@return レポート詳細のビュー、または404ステータス
	@GetMapping("/{id}")
	public String getReportById(@PathVariable Long id, Model model) {
		Optional<Reports> report = reportService.getReportById(id);
		if (report.isPresent()) {
			model.addAttribute("report", report.get());
			return "reports/detail"; // 返回视图名称，例如 "reports/detail"
		} else {
			return "redirect:/reports"; // 如果找不到，重定向到报告列表页面
		}
	}

	// 新しいレポートを作成するフォームの表示
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("report", new Reports());
		return "reports/new"; // 返回视图名称，例如 "reports/new"
	}

	// 新しいレポートを作成し、@param report レポートエンティティ、@return 作成されたレポートの詳細ビュー
	@PostMapping
	public String createReport(Reports report) {
		reportService.createReport(report);
		return "redirect:/reports"; // 成功后重定向到报告列表页面
	}

	// レポートを受領し、@param id レポートID、@return 受領されたレポートのビュー、または404ステータス
	@PostMapping("/{id}/accept")
	public String acceptReport(@PathVariable Long id, Model model) {
		Optional<Reports> acceptedReport = reportService.acceptReport(id);
		if (acceptedReport.isPresent()) {
			model.addAttribute("report", acceptedReport.get());
			return "reports/detail"; // 返回视图名称，例如 "reports/detail"
		} else {
			return "redirect:/reports"; // 如果找不到，重定向到报告列表页面
		}
	}
}
