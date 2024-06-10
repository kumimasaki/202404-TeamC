package rp.com.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rp.com.models.entity.Reports;
import rp.com.services.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportDetailsController {
	@Autowired
	private ReportsService reportService;

	// すべてのレポートを取得し、@return レポートリスト
	@GetMapping
	public List<Reports> getAllReports() {
		return reportService.getAllReports();
	}

	// IDでレポートを取得し、@param id レポートID、@return レポートを含むレスポンスエンティティ、または404ステータス
	@GetMapping("/{id}")
	public ResponseEntity<Reports> getReportById(@PathVariable Long id) {
		Optional<Reports> report = reportService.getReportById(id);
		return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// 新しいレポートを作成し、@param report レポートエンティティ、@return 作成されたレポート
	@PostMapping
	public Reports createReport(@RequestBody Reports report) {
		return reportService.createReport(report);
	}

	// レポートを受領し、@param id レポートID、@return 受領されたレポートを含むレスポンスエンティティ、または404ステータス
	@PostMapping("/{id}/accept")
	public ResponseEntity<Reports> acceptReport(@PathVariable Long id) {
		Optional<Reports> acceptedReport = reportService.acceptReport(id);
		return acceptedReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
