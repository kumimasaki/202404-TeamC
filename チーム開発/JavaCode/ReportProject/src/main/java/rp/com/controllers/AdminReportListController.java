package rp.com.controllers;

import java.util.List;
import java.util.logging.Logger; // 导入Logger类

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

@Controller
public class AdminReportListController {

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private HttpSession session;
   
    
    @GetMapping("/admin/reports")
    public String showReportList(Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        
        if (admin == null) {
            return "redirect:/admin/login";
        } else {
            List<Reports> reports = reportsService.getAllReports();
            model.addAttribute("reports", reports);
            model.addAttribute("admin", admin);
            
            String adminIconPath = "/uploads/" + admin.getAdminIcon(); // 确保路径格式正确
            model.addAttribute("adminIconPath", adminIconPath);
            return "admin_reports";
        }
    }
    
    @PostMapping("/delete_report")
    public String deleteReport(@RequestParam("reportId") Long reportId) {
        reportsService.deleteReport(reportId);
        return "redirect:/admin/reports";
    }
}
