package rp.com.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rp.com.models.dao.ReportsDao;
import rp.com.models.entity.Reports;

@Service
public class ReportsService {

	@Autowired
	private ReportsDao reportsDao;

	// すべてのレポートを取得し、 @return レポートリスト
	public List<Reports> getAllReports() {
		return reportsDao.findAll();
	}

	// IDでレポートを取得し、@param id レポートID、@return レポートのオプショナル
	public Optional<Reports> getReportById(Long id) {
		return reportsDao.findById(id);
	}

	// 管理员ID获取报告列表
	public List<Reports> getReportsByAdminId(Long adminId) {
	    return reportsDao.findByAdminId(adminId);
	}
	
	// 新しいレポートを作成し、@param report レポートエンティティ、@return 作成されたレポート
	public Reports createReport(Reports report) {
		return reportsDao.save(report);
	}

	// IDでレポートを削除し、@param id レポートID
	public void deleteReport(Long id) {
		reportsDao.deleteById(id);
	}

	// IDでレポートを受領し、@param id レポートID、@return 受領されたレポートのオプショナル
	public Optional<Reports> acceptReport(Long id) {
		Optional<Reports> reportOpt = reportsDao.findById(id);
		if (reportOpt.isPresent()) {
			Reports report = reportOpt.get();
			report.setReceiptFlg(1); // 受領フラグを1に設定
			reportsDao.save(report);
			return Optional.of(report);
		} else {
			return Optional.empty();
		}
	}

	// タイトルでレポートを検索し、@param title レポートのタイトル、@return 検索結果のレポートリスト
	public List<Reports> searchReportsByTitle(String title) {
		return reportsDao.findByReportTitleContaining(title);
	}

	// コンテンツでレポートを検索し、@param content レポートの内容、@return 検索結果のレポートリスト
	public List<Reports> searchReportsByContent(String content) {
		return reportsDao.findByContentsOfReportContaining(content);
	}

	// レポートを保存し、@param report レポートエンティティ、@return 保存されたレポート
	public Reports saveReport(Reports report) {
		return reportsDao.save(report);
	}

	// レポートを非表示にするメソッドを追加
	@Transactional
	public void hideReportById(Long reportId) {
		Optional<Reports> reportOpt = reportsDao.findById(reportId);
		reportOpt.ifPresent(report -> {
			report.setDeleteFlg(1); // 非表示フラグを1に設定
			reportsDao.save(report);
		});
	}

	// タイトルまたはコンテンツでレポートを検索するメソッドを追加
	public List<Reports> searchReportsByTitleOrContent(String query) {
		return reportsDao.findByReportTitleContainingOrContentsOfReportContaining(query, query);
	}
}
