package rp.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rp.com.models.entity.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long>{

	// 指定されたメールアドレスに基づいて管理者情報を検索するメソッド
    // SQL: SELECT * FROM admin WHERE admin_email = ?
    // 用途：管理者の登録処理をするときに、同じメールアドレスはあってはならない
	Admin findByAdminEmail(String adminEmail);

	Admin save(Admin admin);
	
	Admin findByAdminName(String adminName);
    // 指定されたメールアドレスとパスワードに基づいて管理者情報を検索するメソッド
    // SQL: SELECT * FROM admin WHERE admin_email = ? AND admin_password = ?
    // 用途：管理者のログイン処理を行う際に、メールアドレスとパスワードが一致するか確認する
    Admin findByAdminEmailAndAdminPassword(String adminEmail, String adminPassword);
    
    
}