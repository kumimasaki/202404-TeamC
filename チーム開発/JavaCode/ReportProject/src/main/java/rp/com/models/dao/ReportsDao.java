package rp.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rp.com.models.entity.Reports;

@Repository
public interface ReportsDao extends JpaRepository<Reports, Long> {
	// レポートの保存と更新
	Reports save(Reports reports);

	// report_idでレポートを検索
	Optional<Reports> findById(Long reportId);

	// すべてのレポートを検索
	List<Reports> findAll();

	// user_idでレポートを検索
	List<Reports> findByUserId(Long userId);

	// admin_idでレポートを検索
	List<Reports> findByAdminId(Long adminId);

	// report_idでレポートを削除
	void deleteById(Long reportId);

	// キーワードを含むタイトルのレポートを検索
	List<Reports> findByReportTitleContaining(String keyword);

	// receipt_flgでレポートを検索
	List<Reports> findByReceiptFlg(int receiptFlg);

	// delete_flgでレポートを検索
	List<Reports> findByDeleteFlg(int deleteFlg);
}
