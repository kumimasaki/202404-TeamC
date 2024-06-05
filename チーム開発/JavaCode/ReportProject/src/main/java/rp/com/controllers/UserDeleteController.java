package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.services.UserService;

@Controller
public class UserDeleteController {

    @Autowired
    private UserService userService;

    // ユーザーを削除するメソッド
    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam("userId") Long userId) {
        // 指定されたIDのユーザーを削除します
        userService.deleteUser(userId);
        // ユーザー一覧ページにリダイレクトします
        return "redirect:/user_list";
    }
}