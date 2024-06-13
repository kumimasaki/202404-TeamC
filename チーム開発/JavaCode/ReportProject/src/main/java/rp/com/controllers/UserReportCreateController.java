package rp.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Reports;
import rp.com.models.entity.Users;
import rp.com.services.AdminService;
import rp.com.services.ReportsService;

@Controller
@RequestMapping
public class UserReportCreateController {

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private AdminService adminService;
    
    @Autowired
	private HttpSession session;
    
	// 報告登録画面を表示し、@return 報告登録画面のテンプレート名
    @GetMapping("/user/report/create")
    public String showReportCreateForm(Model model) {
    	// ログインしているユーザーの情報を取得
        Users users = (Users) session.getAttribute("loginUsersInfo");
      
        List<Reports> reportList = reportsService.getAllReports(); 
        model.addAttribute("reportList", reportList); 
       
     // ページに必要なデータを渡す
        List<String> adminNames = adminService.getAllAdminNames();
        model.addAttribute("adminNames", adminNames);
        model.addAttribute("user", users);
        model.addAttribute("admin", new Admin()); 
        return "user_report_register";
    }

	// 報告登録処理を行う、

    @PostMapping("/user/report/create/process")
    public String processReportCreate(
            @RequestParam("reportTitle") String reportTitle,
            @RequestParam("reportFileName") MultipartFile reportFileName,
            @RequestParam("contentsOfReport") String contentsOfReport,
            @RequestParam("adminName") String adminName,
            Model model,
            HttpSession session) {
        
        Users currentUser = (Users) session.getAttribute("loginUserInfo");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        Admin admin = adminService.getAdminByName(adminName);
        
        Reports report = new Reports();
        report.setReportTitle(reportTitle);
        report.setReportFileName(reportFileName.getOriginalFilename()); 
        report.setContentsOfReport(contentsOfReport);
        
        if (admin != null) {
            report.setAdminId(admin.getAdminId()); 
        } else {
            // 处理管理员对象不存在的情况，例如抛出异常或记录日志
        }      
        report.setUserId(currentUser.getUserId());        
        reportsService.createReport(report);
        
        // 文件上传处理
        if (!reportFileName.isEmpty()) {
            try {
                byte[] bytes = reportFileName.getBytes();
                Path path = Paths.get("src/main/resources/static/uploads/directory/" + reportFileName.getOriginalFilename());
                Files.write(path, bytes);
                // 文件上传成功，进行其他处理...
                model.addAttribute("message", "ファイルがアップロードされました。");
            } catch (IOException e) {
                e.printStackTrace();
                // 文件上传失败，进行错误处理...
                model.addAttribute("message", "ファイルのアップロードに失敗しました。");
            }
        } else {
            // 文件为空，进行错误处理...
            model.addAttribute("message", "ファイルが存在しません。");
        }
        
        return "redirect:/user/report_list";
    }


}
