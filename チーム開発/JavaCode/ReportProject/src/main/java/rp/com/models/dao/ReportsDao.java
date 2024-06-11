package rp.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rp.com.models.entity.Reports;

@Repository
public interface ReportsDao extends JpaRepository<Reports, Long> {

	// レポートの保存と更新
	@Override
	Reports save(Reports reports);

	// report_idでレポートを検索
	@Override
	Optional<Reports> findById(Long reportId);

	// すべてのレポートを検索
	@Override
	List<Reports> findAll();

	// user_idでレポートを検索
	List<Reports> findByUserId(Long userId);

	// admin_idでレポートを検索
	List<Reports> findByAdminId(Long adminId);

	// report_idでレポートを削除
	@Override
	void deleteById(Long reportId);

	// タイトルにキーワードが含まれるレポートを検索
	List<Reports> findByReportTitleContaining(String title);
	
	// コンテンツにキーワードが含まれるレポートを検索
	List<Reports> findByContentsOfReportContaining(String content);
	
	 // タイトルまたはコンテンツでレポートを検索
	List<Reports> findByReportTitleContainingOrContentsOfReportContaining(String title, String content);

	// receipt_flgでレポートを検索
	List<Reports> findByReceiptFlg(int receiptFlg);

	// delete_flgでレポートを検索
	List<Reports> findByDeleteFlg(int deleteFlg);

	// report_idでレポートを削除フラグを設定
	@Query("UPDATE Reports r SET r.deleteFlg = 1 WHERE r.reportId = :reportId")
	@Modifying
	@Transactional
	void markReportAsDeleted(@Param("reportId") Long reportId);
}
