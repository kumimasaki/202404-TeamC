package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserLogoutController {

    @Autowired
    private HttpSession session;
    
    //logout処理
    @GetMapping("/user/logout")
    public String userLogout() {
        //sessionの無効化
        session.invalidate();
        return "redirect:/user/login";
    }
}