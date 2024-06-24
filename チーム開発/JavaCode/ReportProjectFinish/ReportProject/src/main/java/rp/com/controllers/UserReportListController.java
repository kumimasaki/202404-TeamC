package rp.com.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Reports;
import rp.com.models.entity.Users;
import rp.com.services.AdminService;
import rp.com.services.ReportsService;

@Controller
@RequestMapping("/user/report")
public class UserReportListController {

    @Autowired
    private ReportsService reportsService;
    @Autowired
    private HttpSession session;
    @Autowired
    private AdminService adminService;

    // レポート一覧画面を表示するメソッド
    @GetMapping("/list")
    public String showReportList(Model model) {
        Users users = (Users) session.getAttribute("loginUserInfo");
        if (users == null) {
            return "redirect:/user/login";
        } else {
            // ログインユーザーのdeleteFlgが0のレポートリストを取得してモデルに追加する
            List<Reports> reports = reportsService.getReportsByUserIdAndDeleteFlg(users.getUserId(), 0);
            model.addAttribute("reports", reports);
            model.addAttribute("users", users);
            
            if (!reports.isEmpty()) {
                Long adminId = reports.get(0).getAdminId(); 
                Admin admin = adminService.getAdminById(adminId); 
                model.addAttribute("admin", admin);
            }
            
            List<String> adminNames = new ArrayList<>();
            for (Reports report : reports) {
                Long adminId = report.getAdminId(); 
                Admin admin = adminService.getAdminById(adminId); 
                if (admin != null) {
                    adminNames.add(admin.getAdminName());
                } else {
                    adminNames.add("Unknown Admin");
                }
            }
            model.addAttribute("adminNames", adminNames);
            
            
            return "user_reports";
        }
    }

    // レポート検索の処理
    @GetMapping("/search")
    public String searchReports(@RequestParam("keyword") String keyword, Model model) {
        Users users = (Users) session.getAttribute("loginUserInfo");
        if (users == null) {
            return "redirect:/user/login";
        } else {
            List<Reports> searchResults = reportsService.searchReportsByTitleOrContent(keyword);
            model.addAttribute("searchResults", searchResults);
            List<Reports> reports = reportsService.getReportsByUserIdAndDeleteFlg(users.getUserId(), 0);
            model.addAttribute("reports", reports);
            model.addAttribute("users", users);
            return "user_reports";
        }
    }
}
