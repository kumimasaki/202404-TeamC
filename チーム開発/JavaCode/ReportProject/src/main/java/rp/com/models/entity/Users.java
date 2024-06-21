package rp.com.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// このクラスがデータベースのテーブルに対応することを示します
@Entity
// 自動的にゲッターやセッターなどを作ってくれる便利なアノテーションです
@Data
// 引数なしのコンストラクタを自動で作ります
@NoArgsConstructor
// 全てのフィールドを引数に持つコンストラクタを自動で作ります
@AllArgsConstructor
// @NonNullとついているフィールドを引数に持つコンストラクタを自動で作ります
@RequiredArgsConstructor
// このクラスが対応するテーブルの名前を指定します
@Table(name = "users")
public class Users {

	// これは主キーという特別なIDです
	@Id
	// IDを自動で増やしていく設定です
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// テーブルのカラム（列）を指定します
	@Column(name = "user_id")
	private Long userId;

	// このフィールドは必ず値が必要です
	@NonNull
	// ユーザー名という列を作り、必ず値が入るようにします
	@Column(name = "user_name", nullable = false)
	private String userName;

	// このフィールドは必ず値が必要です
	@NonNull
	// メールアドレスという列を作り、必ず値が入るようにします
	@Column(name = "user_email", nullable = false)
	private String userEmail;

	// このフィールドは必ず値が必要です
	@NonNull
	// パスワードという列を作り、必ず値が入るようにします
	@Column(name = "user_password", nullable = false)
	private String userPassword;

	// 削除フラグという列を作り、必ず値が入るようにします
	@Column(name = "delete_flg", nullable = false)
	private int deleteFlg = 0;

	// 作成日時という列を作ります。更新はできません
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@Column(name = "user_icon")
	private String userIcon;

	// 管理者ID（外部キー）という列を作り、必ず値が入るようにします
	@NonNull
	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private Admin admin;


	public Users(String userName, String userEmail, String userPassword, LocalDateTime createdAt, String userIcon, Admin admin) {
	    this.userName = userName;
	    this.userEmail = userEmail;
	    this.userPassword = userPassword;
	    this.createdAt = createdAt;
	    this.userIcon = userIcon;
	    this.admin = admin;
	}

}
