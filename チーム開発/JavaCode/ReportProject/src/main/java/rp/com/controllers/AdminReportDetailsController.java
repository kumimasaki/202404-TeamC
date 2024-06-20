package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import rp.com.models.entity.Reports;
import rp.com.models.entity.Admin;
import rp.com.services.ReportsService;
import rp.com.services.AdminService;

import java.util.Optional;

@Controller
@RequestMapping("/admin/report")
public class AdminReportDetailsController {

    @Autowired
    private ReportsService reportsService;
    
    @Autowired
    private AdminService adminService;  // Add the admin service to fetch admin details

    // IDでレポートを取得し、詳細画面を表示する
    @GetMapping("/details/{reportId}")
    public String getReportById(@PathVariable("reportId") Long reportId, Model model) {
        Optional<Reports> report = reportsService.getReportById(reportId);
        if (report.isPresent()) {
            model.addAttribute("report", report.get());
            
            
            Admin admin = adminService.getAdminById(reportId); 
            model.addAttribute("admin", admin);

            return "admin_report_details";
        } else {
            return "redirect:/admin/report/list";
        }
    }

    // 新しいレポートを作成するフォームの表示
    @GetMapping("/new")
    public String showCreateReportForm(Model model) {
        model.addAttribute("report", new Reports());
        return "admin_report_create";
    }

    // 新しいレポートを作成する
    @PostMapping
    public String createReport(@ModelAttribute Reports report) {
        reportsService.createReport(report);
        return "redirect:/admin/report/list";
    }

    @PostMapping("/receipt/process")
    public String receiveReport(@RequestParam("reportId") Long reportId) {
        reportsService.acceptReport(reportId);
        return "redirect:/admin/report/list";
    }
}
