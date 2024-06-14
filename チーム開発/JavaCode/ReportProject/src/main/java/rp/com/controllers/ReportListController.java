package rp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Reports;
import rp.com.services.AdminService;
import rp.com.services.ReportsService;

@Controller
public class ReportListController {

	@Autowired
	private HttpSession session;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ReportsService reportsService;

	// レポート一覧画面を表示するメソッド
	@GetMapping("/admin/report/list")
	public String showReportList(Model model) {
	    Admin admin = (Admin) session.getAttribute("loginAdminInfo");
	    
	    if (admin == null) {
	        return "redirect:/admin/login";
	    } else {	      
	       
	        List<Reports> reportList = reportsService.getReportsByAdminId(admin.getAdminId());
	        model.addAttribute("reportList", reportList);
	        
	        String iconPath = "admin_icon.jpg";
	        String adminName = "Admin Name";

	        // 将管理员的图标路径和名称添加到模型中
	        model.addAttribute("iconPath", iconPath);
	        model.addAttribute("adminName", adminName);
	        
	        
	        return "admin_reports.html";
	    }
	}

	// レポート検索を処理するメソッド
	@PostMapping("/admin/search_reports")
	public String serchReports(@RequestParam("search") String search, Model model) {
		// キーワードでレポートを検索
		List<Reports> reportList = reportsService.searchReportsByContent(search);
		// 検索結果をモデルに追加
		model.addAttribute("reportList", reportList);
		// レポート一覧画面のテンプレートを返す
		return "admin_reports";
	}

	// レポートを削除するメソッド
	@GetMapping("/delete_report/{reportId}")
	    public String deleteReport(@PathVariable("reportId") Long reportId) {
	        // 删除指定ID的报告
	        reportsService.deleteReport(reportId);
	        // 返回报告列表页面
	        return "redirect:/admin/report/list";
	    }
}
