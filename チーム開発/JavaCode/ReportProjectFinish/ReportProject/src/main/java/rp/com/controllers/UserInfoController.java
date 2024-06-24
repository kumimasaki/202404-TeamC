package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import rp.com.services.UserService;
import rp.com.models.entity.Users;

@Controller
public class UserInfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    // user情報画面を表示するメソッド
    @GetMapping("/user/info/edit/{userId}")
    public String showEditUserInfoForm(@PathVariable Long userId, Model model) {
        Users users = (Users) session.getAttribute("loginUserInfo");
        if (users != null) {
            // ユーザー情報をモデルに追加
            model.addAttribute("users", users);
            return "user_info_edit";
        } else {
            // エラーメッセージをモデルに追加
            model.addAttribute("error", "ユーザーが見つかりません");
            return "error";
        }
    }

    // 情報変更処理を行うメソッド
    @PostMapping("/user/info/update")
    public String updateUserInfo(@RequestParam("userId") Long userId,
                                 @RequestParam("username") String userName,
                                 @RequestParam("userEmail") String userEmail,
                                 @RequestParam("userPassword") String userPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {

        // パスワード確認
        if (!userPassword.equals(confirmPassword)) {
            model.addAttribute("error", "パスワードが一致しません");
            return "user_info_edit";
        }
        // ユーザーIDに基づいてユーザー情報を取得
        Users user = userService.getUserById(userId);
        if (user != null) {
            user.setUserName(userName);
            user.setUserEmail(userEmail);
            user.setUserPassword(userPassword);
            userService.updateUser(user);
            // 情報変更画面にリダイレクト
            return "redirect:/user/info/edit/" + userId + "?success";
        } else {
            model.addAttribute("error", "ユーザーが見つかりません");
            return "error";
        }
    }
}

