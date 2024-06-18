package rp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

@RequestMapping("/user/report")
public class UserReportListController {

    @Autowired
    private ReportsService reportsService;
    @Autowired
    private UserService userService;
    @Autowired
	private HttpSession session;
	// レポート一覧画面を表示するメソッド
    @GetMapping("/list")
    public String showReportList(Model model) {
    	Users users = (Users) session.getAttribute("loginUserInfo");	
    	if(users == null) {
    		return "redirect:/user/login";
    	}else {
    		
        	// レポートのリストを取得してモデルに追加する
            List<Reports> reports = reportsService.getAllReports();
            model.addAttribute("reports", reports);
            model.addAttribute("users", users);
            return "user_reports.html";
    	}
    
    }

    // レポート検索を処理するメソッド
    @PostMapping("/search")
    public String searchReports(@RequestParam("keyword") String keyword, Model model) {
        // キーワードでレポートを検索
        List<Reports> reportList = reportsService.searchReportsByContent(keyword);
        // 検索結果をモデルに追加
        model.addAttribute("reports", reportList);
        // レポート一覧画面のテンプレートを返す
        return "user_reports.html";
    }


    @PostMapping("/delete/{reportId}")
    	public String deleteReport(@RequestParam("reportId") Long reportId) {
    		// 指定されたIDのレポートを削除
    		reportsService.deleteReport(reportId);
      
        return "redirect:/user/report/list";
    }


}