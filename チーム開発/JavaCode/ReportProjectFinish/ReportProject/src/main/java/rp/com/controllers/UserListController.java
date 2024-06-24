package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import rp.com.services.UserService;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Reports;
import rp.com.models.entity.Users;

import java.util.List;
import java.util.Optional;

@Controller
public class UserListController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;

	// ユーザー一覧画面を表示するメソッド
	@GetMapping("/user/list")
	public String showUserList(Model model) {
		 // 管理者情報をセッションから取得
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		if (admin != null) {
			// 管理者情報をモデルに追加
			model.addAttribute("adminId", admin.getAdminId());
			model.addAttribute("adminName", admin.getAdminName());
			model.addAttribute("adminIconPath", admin.getAdminIconPath());
			// すべてのユーザーリストを取得
			List<Users> usersList = userService.getAllUserList();
			// ユーザーリストをモデルに追加
			model.addAttribute("usersList", usersList);
			model.addAttribute("users", new Users());
			return "user_list";
		} else {
			// 管理者がログインしていない場合、ログイン画面にリダイレクト
			return "redirect:/admin/login";
		}
	}

	// ユーザー検索を処理するメソッド
	@PostMapping("/user/search")
	public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
	    // 获取登录的管理员信息
	    Admin admin = (Admin) session.getAttribute("loginAdminInfo");
	    if (admin != null) {
	        // 根据关键字搜索用户
	        List<Users> searchResults = userService.searchUserByNameOrEmail(keyword);
	        
	        // 将搜索结果和管理员信息添加到模型中
	        model.addAttribute("adminId", admin.getAdminId());
	        model.addAttribute("adminName", admin.getAdminName());
	        model.addAttribute("adminIconPath", admin.getAdminIconPath());
	        model.addAttribute("searchResults", searchResults);  // 添加搜索结果到模型中

	        return "user_list";  // 返回用户列表视图
	    } else {
	        // 如果管理员未登录，重定向到登录页面
	        return "redirect:/admin/login";
	    }
	}



//		名前またはメールアドレスでユーザーを検索
//      IDに基づいてレポートを取得
//		Optional<Users> userOptional = userService.getUserById(userId);
//		List<Users> usersList = userService.searchUsersByNameOrEmail(search);
//		検索結果をモデルに追加
//		model.addAttribute("usersList", usersList);
//		user_list.htmlテンプレートを返す
//		return "user_list";
//	}

	// ユーザーを削除するメソッド
	@PostMapping("/user/delete")
	public String deleteUser(@RequestParam("userId") Long userId) {
		// 指定されたIDのユーザーを削除
		userService.deleteUser(userId);
		// ユーザー一覧ページにリダイレクトする
		return "redirect:/user/list";
	}
}