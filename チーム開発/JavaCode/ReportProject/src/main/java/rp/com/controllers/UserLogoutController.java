package rp.com.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserLogoutController {

    // ログアウト処理を行うメソッド
    @PostMapping("/user/logout")
    public String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 現在のセッションを取得
        if (session != null) {
            session.invalidate(); // セッションを無効化
        }
        return "redirect:/logout"; // ログインページにリダイレクト
    }
}