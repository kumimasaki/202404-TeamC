package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Reports;
import rp.com.models.entity.Users;
import rp.com.models.entity.Admin;
import rp.com.services.ReportsService;
import rp.com.services.UserService;
import rp.com.services.AdminService;

import java.util.Optional;

@Controller
@RequestMapping("/admin/report")
public class AdminReportDetailsController {

    @Autowired
    private UserService userService;

	@Autowired
    private ReportsService reportsService;
    
    @Autowired
    private HttpSession session;
    
    @Autowired
    private AdminService adminService;  // Add the admin service to fetch admin details

    // IDでレポートを取得し、詳細画面を表示する
    @GetMapping("/details/{reportId}")
    public String showReportDetails(@PathVariable("reportId") Long reportId, Model model) {
		 // 管理者情報をセッションから取得
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		Users users = (Users) session.getAttribute("loginUserInfo");
		
		Optional<Reports> reportOptional = reportsService.getReportById(reportId);
		if (reportOptional.isPresent()) {
			Reports report = reportOptional.get();
			model.addAttribute("report", report);
		    model.addAttribute("users", users);	
		    
		        Long userId = report.getUserId(); 
		        Users user = userService.getUserById(userId); 
		        
		        model.addAttribute("user", user);        
	        model.addAttribute("admin", admin);
			return "admin_report_details"; // ビュー名を返す
		} else {
			// レポートが見つからない場合、レポート一覧ページにリダイレクト
			return "redirect:/admin/report/list";
		}
	}

    @PostMapping("/receipt/process")
    public String receiveReport(@RequestParam("reportId") Long reportId) {
        reportsService.acceptReport(reportId);
        return "redirect:/admin/report/list";
    }
}
