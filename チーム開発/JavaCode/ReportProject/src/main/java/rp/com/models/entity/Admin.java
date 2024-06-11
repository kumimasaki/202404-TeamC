package rp.com.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
// @NonNullとついているフィールドを引数に持つコンストラクタを自動で作ります

// このクラスが対応するテーブルの名前を指定します
@Table(name = "admin")
public class Admin {

    // これは主キーという特別なIDです
    @Id
    // IDを自動で増やしていく設定です
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // テーブルのカラム（列）を指定します
    @Column(name = "admin_id")
    private Long adminId;

    // このフィールドは必ず値が必要です
    @NonNull
    // 名前という列を作り、必ず値が入るようにします
    @Column(name = "admin_name", nullable = false)
    private String adminName;

    // このフィールドは必ず値が必要です
    @NonNull
    // メールアドレスという列を作り、必ず値が入るようにします
    @Column(name = "admin_email", nullable = false)
    private String adminEmail;

    // このフィールドは必ず値が必要です
    @NonNull
    // パスワードという列を作り、必ず値が入るようにします
    @Column(name = "admin_password", nullable = false)
    private String adminPassword;

    // このフィールドは必ず値が必要です
    @NonNull
    // アイコンという列を作り、必ず値が入るようにします
    @Column(name = "admin_icon", nullable = false)
    private String adminIcon;

    // 新增的确认密码字段
    @Column(name = "confirm_password")
    private String confirmPassword;
    
    // 作成日時という列を作ります。更新はできません
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 確認フラグという列を作り、必ず値が入るようにします
    @Column(name = "confirm_flg", nullable = false)
    private int confirmFlg = 0;

    // コンストラクタを追加します
    public Admin(String adminName, String adminEmail, String adminPassword, String adminIcon,String confirmPassword) {
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminIcon = adminIcon;
        this.confirmPassword = confirmPassword;
    }
}
