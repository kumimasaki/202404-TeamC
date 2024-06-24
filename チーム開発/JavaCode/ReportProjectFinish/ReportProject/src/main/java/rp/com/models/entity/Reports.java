package rp.com.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "reports")
@Entity
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String reportTitle;
    private String reportFileName;
    private String contentsOfReport;
    private LocalDateTime createdAt = LocalDateTime.now();
    private int deleteFlg = 0;
    private int receiptFlg = 0;
    private Long adminId;
    private Long userId;
    private String userName;
    private String adminName;

    // コンストラクタ、ゲッター、セッターを含む
    public Reports() {}

    public Reports(Long reportId, String reportTitle, String reportFileName, String contentsOfReport,
                   LocalDateTime createdAt, int deleteFlg, int receiptFlg, Long adminId, Long userId, String userName, String adminName) {
        this.reportId = reportId;
        this.reportTitle = reportTitle;
        this.reportFileName = reportFileName;
        this.contentsOfReport = contentsOfReport;
        this.createdAt = createdAt;
        this.deleteFlg = deleteFlg;
        this.receiptFlg = receiptFlg;
        this.adminId = adminId;
        this.userId = userId;
        this.userName = userName;
        this.adminName = adminName;
    }

    // ゲッターとセッター
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public String getContentsOfReport() {
        return contentsOfReport;
    }

    public void setContentsOfReport(String contentsOfReport) {
        this.contentsOfReport = contentsOfReport;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(int deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public int getReceiptFlg() {
        return receiptFlg;
    }

    public void setReceiptFlg(int receiptFlg) {
        this.receiptFlg = receiptFlg;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}

