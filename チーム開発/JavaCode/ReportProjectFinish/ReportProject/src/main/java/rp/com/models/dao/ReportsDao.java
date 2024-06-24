package rp.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rp.com.models.entity.Reports;
import java.util.List;

@Repository
public interface ReportsDao extends JpaRepository<Reports, Long> {

    // レポートの保存と更新
    // SQL: INSERT INTO reports (...) VALUES (...) ON DUPLICATE KEY UPDATE ...
    // 用途：レポートの新規作成と既存レポートの更新を行う
    Reports save(Reports reports);

    // report_idでレポートを検索
    // SQL: SELECT * FROM reports WHERE report_id = ?
    // 用途：指定されたreport_idのレポートを取得する
    Reports findByReportId(Long reportId);

    // すべてのレポートを検索
    // SQL: SELECT * FROM reports
    // 用途：データベース内の全レポートを取得する
    List<Reports> findAll();

    // user_idでレポートを検索
    // SQL: SELECT * FROM reports WHERE user_id = ?
    // 用途：指定されたuser_idに関連するレポートを取得する
    List<Reports> findByUserId(Long userId);

    // admin_idでレポートを検索
    // SQL: SELECT * FROM reports WHERE admin_id = ?
    // 用途：指定されたadmin_idに関連するレポートを取得する
    List<Reports> findByAdminId(Long adminId);

    // report_idでレポートを削除
    // SQL: DELETE FROM reports WHERE report_id = ?
    // 用途：指定されたreport_idのレポートを削除する
    void deleteByReportId(Long reportId);

    // receipt_flgでレポートを検索
    // SQL: SELECT * FROM reports WHERE receipt_flg = ?
    // 用途：指定されたreceipt_flgの値を持つレポートを取得する
    List<Reports> findByReceiptFlg(int receiptFlg);

    // delete_flgでレポートを検索
    // SQL: SELECT * FROM reports WHERE delete_flg = ?
    // 用途：指定されたdelete_flgの値を持つレポートを取得する
    List<Reports> findByDeleteFlg(int deleteFlg);

    // reportTitle または contentsOfReport に基づいてレポートを検索
    // SQL: SELECT * FROM reports WHERE LOWER(report_title) LIKE LOWER('%?%') OR LOWER(contents_of_report) LIKE LOWER('%?%')
    // 用途：指定されたキーワードに基づいてレポートタイトルまたはレポート内容を部分一致検索し、該当するレポートを取得する
    List<Reports> findByReportTitleContainingIgnoreCaseOrContentsOfReportContainingIgnoreCase(String reportTitle, String contentsOfReport);
}
