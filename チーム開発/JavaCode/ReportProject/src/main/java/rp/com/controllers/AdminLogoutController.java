package rp.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLogoutController {

	//ログアウト処理
	@PostMapping("/admin/logout")
	public String logout(HttpServletRequest request) {
		//現在のセッションを取得
		HttpSession session = request.getSession(false);
		//セッションが存在しない場合に
		if (session != null) {
			// セッションを無効化
            session.invalidate(); 
        }
		// ログインページにリダイレクト
		return "redirect:/admin/login";
	}
}
