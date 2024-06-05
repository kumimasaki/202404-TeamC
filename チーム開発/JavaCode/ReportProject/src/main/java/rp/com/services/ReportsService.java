package rp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rp.com.models.dao.ReportsDao;
import rp.com.models.entity.Reports;

import java.util.List;
import java.util.Optional;

@Service
public class ReportsService {

	@Autowired
	private ReportsDao reportsDao;

	// レポートを作成するメソッド
	public Reports createReport(Reports report) {
		return reportsDao.save(report);
	}

	// すべてのレポートを取得するメソッド
	public List<Reports> getAllReports() {
		return reportsDao.findAll();
	}

	// report_idで特定のレポートを取得するメソッド
	public Optional<Reports> getReportById(Long reportId) {
		return reportsDao.findById(reportId);
	}

	// レポートを更新するメソッド
	public Reports updateReport(Reports report) {
		return reportsDao.save(report);
	}

	// report_idでレポートを削除するメソッド
	public void deleteReport(Long reportId) {
		reportsDao.deleteById(reportId);
	}

	// report_idでレポートを削除フラグを設定するメソッド
	@Transactional
	public void markReportAsDeleted(Long reportId) {
		reportsDao.markReportAsDeleted(reportId);
	}

	// user_idでレポートを取得するメソッド
	public List<Reports> getReportsByUserId(Long userId) {
		return reportsDao.findByUserId(userId);
	}

	// admin_idでレポートを取得するメソッド
	public List<Reports> getReportsByAdminId(Long adminId) {
		return reportsDao.findByAdminId(adminId);
	}

	// キーワードを含むタイトルのレポートを検索するメソッド
	public List<Reports> searchReportsByTitle(String keyword) {
		return reportsDao.findByReportTitleContaining(keyword);
	}

	// receipt_flgでレポートを取得するメソッド
	public List<Reports> getReportsByReceiptFlg(int receiptFlg) {
		return reportsDao.findByReceiptFlg(receiptFlg);
	}

	// delete_flgでレポートを取得するメソッド
	public List<Reports> getReportsByDeleteFlg(int deleteFlg) {
		return reportsDao.findByDeleteFlg(deleteFlg);
	}
}
