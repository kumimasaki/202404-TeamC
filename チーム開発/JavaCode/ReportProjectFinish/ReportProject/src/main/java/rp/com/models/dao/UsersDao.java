package rp.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import rp.com.models.entity.Users;

@Repository
public interface UsersDao extends JpaRepository<Users, Long> {

	// ユーザー名またはメールアドレスでユーザーを検索するメソッド
	List<Users> findByUserNameContainingOrUserEmailContaining(String userNameKeyword, String userEmailKeyword);
	
	// 保存処理と更新処理 insertとupdate
	Users save(Users users);

	// SELECT * FROM users WHERE users_email =?
	// 用途：ユーザーの追加処理をするときに、同じメールアドレスがあったらば追加させいないようにする。
	Users findByUserEmail(String userEmail);

	// SELECT * FROM users WHERE user_email = ?AND user_password=?
	// 用途：ログイン処理に使用、入力したメールアドレスとパスワードが一致してる時だけデータを取得する
	Users findByUserEmailAndUserPassword(String userEmail, String userPassword);
	
	Users findByUserId(Long userId);
}
//	// admin_idでuserを検索
//    // SQL: SELECT * FROM users WHERE admin_id = ?
//    // 用途：指定されたadmin_idに関連するレポートを取得する
//    List<Users> findByAdminId(Long adminId);
//}
