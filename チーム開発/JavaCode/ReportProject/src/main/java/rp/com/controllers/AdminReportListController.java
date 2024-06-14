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
    @GetMapping("/admin/reports")
    public String showReportList(Model model) {
    	List<Reports> reports = reportsService.getAllReports();
		model.addAttribute("reports", reports);
		return "admin_reports";
    }

  

    // レポートを削除するメソッド
    @PostMapping("/delete_report")
    public String deleteReport(@RequestParam("reportId") Long reportId) {
        // 指定されたIDのレポートを削除する
        reportsService.deleteReport(reportId);
        // レポート一覧画面にリダイレクトする
        return "redirect:/admin/reports";
    }
}