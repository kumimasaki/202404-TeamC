package rp.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import rp.com.services.AdminService;
import rp.com.services.UserService;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Users;

@Controller
public class UserCreateController {

    @Autowired
    private AdminService adminService;

    // UserServiceのインスタンスを自動的に注入します
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    // ユーザー登録画面を表示するメソッド
    @GetMapping("/user/create")
    public String showCreateUserForm(Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        if (admin != null) {
            model.addAttribute("adminId", admin.getAdminId());
            return "user_create.html";
        } else {
            return "redirect:/admin/login";
        }
    }

    // ユーザー登録処理を行うメソッド
    @PostMapping("/admin/user/create/process")
    public String createUser(@RequestParam String userName, @RequestParam String userEmail,
                             @RequestParam String userPassword, @RequestParam String confirmPassword,
                             @RequestParam("userIcon") MultipartFile userIcon,
                             @RequestParam("adminId") Long adminId, Model model) {

        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        // パスワード確認
        if (!userPassword.equals(confirmPassword)) {
            model.addAttribute("error", "パスワードが一致しません");
            return "user_create.html";
        }

        // アイコンを保存する
        String fileName = null;
        if (!userIcon.isEmpty()) {
            try {
                String originalFilename = userIcon.getOriginalFilename();
                fileName = new SimpleDateFormat("yyyy-MM-dd-HH-").format(new Date()) + originalFilename;
                Files.copy(userIcon.getInputStream(), Path.of("src/main/resources/static/uploads/" + fileName));
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "アイコンの保存に失敗しました");
                return "user_create.html";
            }
        }

        // 新しいユーザー作成
        Users newUser = new Users(userName, userEmail, userPassword, admin);
        newUser.setUserIcon(fileName);

        // 新しいユーザー保存
        if (userService.createUser(userName, userEmail, userPassword, admin)) {
            return "redirect:/user/list";
        } else {
            model.addAttribute("error", "ユーザー追加が失敗しました。");
            return "user_create.html";
        }
    }
}