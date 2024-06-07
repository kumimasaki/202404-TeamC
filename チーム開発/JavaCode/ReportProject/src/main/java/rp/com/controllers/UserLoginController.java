package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.services.UserService;
import rp.com.models.entity.Users;

@Controller
public class UserLoginController {

    @Autowired
    private UserService userService;

    // ログイン画面を表示するメソッド
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // ログイン情報をモデルに追加します
        model.addAttribute("loginForm", new Users());
        return "login";
    }

    // ログイン処理を行うメソッド
    @PostMapping("/login/process")
    public String loginUser(
            @RequestParam("email") String userEmail,
            @RequestParam("password") String userPassword,
            Model model) {

        // ログイン処理
        Users user = userService.loginCheck(userEmail, userPassword);
        if (user == null) {
            model.addAttribute("error", "メールアドレスまたはパスワードが正しくありません");
            return "login";
        } else {
            // ログイン成功の場合、ユーザー情報をモデルに追加してダッシュボードにリダイレクトします
            model.addAttribute("user", user);
            return "redirect:/dashboard";
        }
    }
}