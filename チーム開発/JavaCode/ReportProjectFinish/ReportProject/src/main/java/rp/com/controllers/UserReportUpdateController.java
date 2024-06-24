package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Reports;
import rp.com.models.entity.Users;
import rp.com.services.ReportsService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			@RequestParam("reportFileName") MultipartFile file, @RequestParam("userName") String userName)
			throws IOException {
		Optional<Reports> reportOptional = reportsService.getReportById(reportId);
		if (reportOptional.isPresent()) {
			Reports report = reportOptional.get();
			report.setReportTitle(title);
			report.setContentsOfReport(contentsOfReport);
			report.setUserName(userName);
			report.setReportFileName(file.getOriginalFilename());

			//if (!file.isEmpty()) {
				// ファイルをファイルシステムに保存
				//String fileName = file.getOriginalFilename();
				//String filePath = "path/to/save/files/" + fileName;
				//file.transferTo(new File(filePath));
			//}
			
			// ファイルアップロード処理
	        if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	                Path path = Paths.get("src/main/resources/static/uploads/directory/" + file.getOriginalFilename());
	                Files.write(path, bytes);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

			reportsService.saveReport(report);
			// 成功時に報告一览ページにリダイレクト
			return "redirect:/user/report/list";
		} else {
			return "redirect:/user/report/list"; // レポートが存在しない場合もレポート一覧ページにリダイレクトする
		}
	}
}
