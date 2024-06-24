package rp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

@Controller
@RequestMapping("/admin")
public class AdminReportListController {

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private HttpSession session;

    // レポート一覧画面を表示するメソッド
    @GetMapping("/report/list")
    public String showReportList(Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdminInfo");

        // 管理者がログインしていない場合、ログインページにリダイレクト
        if (admin == null) {
            return "redirect:/admin/login";
        } else {
            // 管理者IDに基づいてレポートリストを取得し、モデルに追加
            List<Reports> reports = reportsService.getReportsByAdminId(admin.getAdminId());
            model.addAttribute("reports", reports);
            model.addAttribute("admin", admin);
            // 管理者のアイコンパスと名前をモデルに追加
            model.addAttribute("adminIconPath", admin.getAdminIconPath());
            return "admin_reports"; // Correct the view name here
        }
    }

    // レポート検索の処理
    @GetMapping("/search")
    public String searchReports(@RequestParam("keyword") String keyword, Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        if (admin == null) {
            return "redirect:/admin/login";
        } else {
            List<Reports> searchResults = reportsService.searchReportsByTitleOrContent(keyword);
            model.addAttribute("searchResults", searchResults);
            List<Reports> reports = reportsService.getReportsByAdminId(admin.getAdminId());
            model.addAttribute("reports", reports);
            model.addAttribute("admin", admin);
            model.addAttribute("adminIconPath", admin.getAdminIconPath());
            return "admin_reports";
        }
    }

    // レポートを削除するメソッド
    @PostMapping("/delete/report/{reportId}")
    public String deleteReport(@PathVariable("reportId") Long reportId) {
        // 指定されたIDのレポートを削除
        reportsService.deleteReport(reportId);
        return "redirect:/admin/report/list";
    }
}
