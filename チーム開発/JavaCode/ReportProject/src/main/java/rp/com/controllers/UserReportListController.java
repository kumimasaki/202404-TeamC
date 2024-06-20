package rp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Reports;
import rp.com.models.entity.Users;
import rp.com.services.ReportsService;

@Controller
@RequestMapping("/user/report")
public class UserReportListController {

    @Autowired
    private ReportsService reportsService;
    @Autowired
    private HttpSession session;

    // レポート一覧画面を表示するメソッド
    @GetMapping("/list")
    public String showReportList(Model model) {
        Users users = (Users) session.getAttribute("loginUserInfo");
        if (users == null) {
            return "redirect:/user/login";
        } else {
            // ログインユーザーのレポートリストを取得してモデルに追加する
            List<Reports> reports = reportsService.getReportsByUserId(users.getUserId());
            model.addAttribute("reports", reports);
            model.addAttribute("users", users);
            return "user_reports.html";
        }
    }

}

