package rp.com.controllers;

import java.util.ArrayList;
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
import rp.com.models.entity.Users;
import rp.com.services.ReportsService;
import rp.com.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminReportListController {

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private HttpSession session;
    @Autowired
    private UserService userService;
    // レポート一覧画面を表示するメソッド
    @GetMapping("/report/list")
    public String showReportList(Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        Users users = (Users) session.getAttribute("loginUserInfo");
        
        
        // 管理者がログインしていない場合、ログインページにリダイレクト
        if (admin == null) {
            return "redirect:/admin/login";
        } else {
            // 管理者IDに基づいてレポートリストを取得し、モデルに追加
            List<Reports> reports = reportsService.getReportsByAdminId(admin.getAdminId());
            model.addAttribute("reports", reports);
            model.addAttribute("admin", admin);
            model.addAttribute("user", users);

            // 管理者のアイコンパスと名前をモデルに追加
            model.addAttribute("adminIconPath", admin.getAdminIconPath());
			
			// レポートのユーザー情報を取得してモデルに追加
	        List<String> userNames = new ArrayList<>();
	        for (Reports report : reports) {
	            Long userId = report.getUserId(); // レポートに関連するユーザーIDを取得
	            Users user = userService.getUserById(userId); // ユーザー情報を取得
	            if (user != null) {
	                userNames.add(user.getUserName());
	            } else {
	                userNames.add("Unknown User");
	            }
	        }
	        model.addAttribute("userNames", userNames);
            
			
			
			return "admin_reports";
        }
    }

    // レポート検索の処理
    @GetMapping("/search")
    public String searchReports(@RequestParam("keyword") String keyword, Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        Users users = (Users) session.getAttribute("loginUserInfo");
        
        // 管理者がログインしていない場合、ログインページにリダイレクト
        if (admin == null) {
            return "redirect:/admin/login";
        } else {

            
            // 検索キーワードに基づいてレポートを検索し、結果をモデルに追加
            List<Reports> searchResults = reportsService.searchReportsByTitleOrContent(keyword);
            model.addAttribute("searchResults", searchResults);
            
            // 管理者IDに基づいてレポートリストを取得し、モデルに追加
            List<Reports> reports = reportsService.getReportsByAdminId(admin.getAdminId());
            model.addAttribute("reports", reports);
            
            // 管理者情報とアイコンパスをモデルに追加
            model.addAttribute("admin", admin);
            model.addAttribute("adminIconPath", admin.getAdminIconPath());
            
         // 各レポートに対応するユーザー情報を取得してモデルに追加
            List<String> userNames = new ArrayList<>();
            for (Reports report : reports) {
                Long userId = report.getUserId(); // レポートに関連するユーザーIDを取得
                Users user = userService.getUserById(userId); // ユーザー情報を取得
                if (user != null) {
                    userNames.add(user.getUserName());
                } else {
                    userNames.add("Unknown User");
                }
            }
            model.addAttribute("userNames", userNames);
            
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
