package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/user/report/update")
public class UserReportUpdateController {

	@Autowired
	private ReportsService reportsService;

	// 報告修正処理を行うメソッド
	@PostMapping("/process")
	public String processReportUpdate(@RequestParam("reportId") Long reportId, @RequestParam("title") String title,
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

			reportsService.saveReport(report);
			return "redirect:/user/report/details?reportId=" + reportId;
		} else {
			return "redirect:/user/report/list";
		}
	}
}
