package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UserReportDetailsController {

	@Autowired
	private ReportsService reportsService;

	// レポート詳細ページを表示するメソッド
	@GetMapping("/user/report/details")
	public String showReportDetails(@RequestParam("reportId") Long reportId, Model model) {
		// IDに基づいてレポートを取得
		Optional<Reports> reportOptional = reportsService.getReportById(reportId);
		if (reportOptional.isPresent()) {
			Reports report = reportOptional.get();
			model.addAttribute("report", report);
			return "/user/report_details"; // ビュー名を返す
		} else {
			// レポートが見つからない場合、レポート一覧ページにリダイレクト
			return "redirect:/user/report/list";
		}
	}

	// レポート編集ページを表示するメソッド
	@GetMapping("/user/report/edit")
	public String showEditReport(@RequestParam("reportId") Long reportId, Model model) {
		// IDに基づいてレポートを取得
		Optional<Reports> reportOptional = reportsService.getReportById(reportId);
		if (reportOptional.isPresent()) {
			Reports report = reportOptional.get();
			model.addAttribute("report", report);
			return "/user/edit_report"; // ビュー名を返す
		} else {
			// レポートが見つからない場合、レポート一覧ページにリダイレクト
			return "redirect:/user/report/list";
		}
	}

	// レポートの更新を処理するメソッド
	@PostMapping("/user/report/update")
	public String updateReport(@RequestParam("reportId") Long reportId, @RequestParam("title") String title,
			@RequestParam("contentsOfReport") String contentsOfReport,
			@RequestParam("reportFileName") MultipartFile file) throws IOException {
		Optional<Reports> reportOptional = reportsService.getReportById(reportId);
		if (reportOptional.isPresent()) {
			Reports report = reportOptional.get();
			report.setReportTitle(title);
			report.setContentsOfReport(contentsOfReport);

			if (!file.isEmpty()) {
				// ファイルをファイルシステムに保存
				String fileName = file.getOriginalFilename();
				String filePath = "path/to/save/files/" + fileName;
				file.transferTo(new File(filePath));
				report.setReportFileName(fileName);
			}

			reportsService.saveReport(report); // saveReport メソッドを呼び出す
			return "redirect:/user/report/details?reportId=" + reportId;
		} else {
			return "redirect:/user/report/list";
		}
	}
}
