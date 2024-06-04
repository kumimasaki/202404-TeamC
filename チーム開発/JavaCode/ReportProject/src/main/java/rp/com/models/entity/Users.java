package rp.com.models.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Users")
public class Users {
	// user_idの設定
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	// ユーザー名
	@Column(name = "user_name", nullable = false, length = 255)
	private String userName;

	// ユーザーのメールアドレス
	@Column(name = "user_email", nullable = false, length = 255)
	private String userEmail;

	// ユーザーのパスワード
	@Column(name = "user_password", nullable = false, length = 255)
	private String userPassword;

	// 削除フラグ
	@Column(name = "delete_flg", nullable = false)
	private int deleteFlg;

	// 作成日時
	@Column(name = "created_at", nullable = false, updatable = false)
	private Timestamp createdAt;

	// 管理者ID（外部キー）
	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private Admin admin;

	// ゲッターとセッター
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}