package rp.com.models.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NonNull;

// このクラスがデータベースのテーブルに対応することを示します
@Entity
public class Admin {

    // これは主キーという特別なIDです
    @Id
    // IDを自動で増やしていく設定です
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;

    private String adminName;

    private String adminEmail;

    private String adminPassword;

    private String adminIcon;
  
    private LocalDateTime createdAt = LocalDateTime.now();

    private int confirmFlg = 0;

    //空のコンストラクタ
	public Admin() {
		
	}
	//コンストラクタ
	public Admin(@NonNull String adminName, @NonNull String adminEmail, @NonNull String adminPassword,
			@NonNull String adminIcon, LocalDateTime createdAt, int confirmFlg) {
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
		this.adminIcon = adminIcon;
		this.createdAt = createdAt;
		this.confirmFlg = confirmFlg;
	}
	//getter / setter
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getAdminIcon() {
		return adminIcon;
	}
	public void setAdminIcon(String adminIcon) {
		this.adminIcon = adminIcon;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getConfirmFlg() {
		return confirmFlg;
	}
	public void setConfirmFlg(int confirmFlg) {
		this.confirmFlg = confirmFlg;
	}
	
    
    
}
