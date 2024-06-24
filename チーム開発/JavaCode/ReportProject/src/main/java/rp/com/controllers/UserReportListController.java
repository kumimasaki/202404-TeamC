package rp.com.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            // ログインユーザーのdeleteFlgが0のレポートリストを取得してモデルに追加する
            List<Reports> reports = reportsService.getReportsByUserIdAndDeleteFlg(users.getUserId(), 0);
            model.addAttribute("reports", reports);
            model.addAttribute("users", users);
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
