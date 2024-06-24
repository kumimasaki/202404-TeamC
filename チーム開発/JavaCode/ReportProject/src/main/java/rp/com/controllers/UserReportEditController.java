package rp.com.controllers;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user/report")
public class UserReportEditController {

    @Autowired
    private ReportsService reportsService;

    // 指定されたIDのレポートを編集するフォームを表示するメソッド
    @GetMapping("/editReport")
    public String showEditReportForm(@RequestParam Long reportId, Model model) {
        Optional<Reports> report = reportsService.getReportById(reportId);
        if (report.isPresent()) {
            model.addAttribute("report", report.get());
            return "user_report_edit"; // レポート編集ページに遷移する
        } else {
            return "redirect:/user/report/list"; // レポートが存在しない場合、レポート一覧ページにリダイレクトする
        }
    }
}
