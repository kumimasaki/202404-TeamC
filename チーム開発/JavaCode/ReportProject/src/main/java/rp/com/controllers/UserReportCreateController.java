package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

@Controller
@RequestMapping("/user/report")
public class UserReportCreateController {

	@Autowired
	private ReportsService reportsService;

	// 報告登録画面を表示し、@return 報告登録画面のテンプレート名
	@GetMapping("/create")
	public String showReportCreateForm() {
		return "user/report_create";
	}

	// 報告登録処理を行う、@param reportTitle報告のタイトル
	// @param reportFileName報告ファイルの名前
	// @param contentsOfReport 報告内容
	// @param modelモデル
	// @return 登録結果画面のテンプレート名
	@PostMapping("/create/process")
	public String processReportCreate(@RequestParam("reportTitle") String reportTitle,
			@RequestParam("reportFileName") String reportFileName,
			@RequestParam("contentsOfReport") String contentsOfReport, Model model) {
		// レポートエンティティを作成
		Reports report = new Reports();
		report.setReportTitle(reportTitle);
		report.setReportFileName(reportFileName);
		report.setContentsOfReport(contentsOfReport);
		// レポートを保存
		reportsService.createReport(report);
		// モデルにメッセージを追加
		model.addAttribute("message", "レポートが正常に登録されました。");
		// 登録結果画面のテンプレートを返す
		return "user/report_create_result";
	}
}
