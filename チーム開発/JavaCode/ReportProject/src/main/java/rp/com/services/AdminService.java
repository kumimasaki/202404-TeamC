package rp.com.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import rp.com.models.dao.AdminDao;
import rp.com.models.entity.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    // 管理者の登録処理
    public void saveAdminWithIcon(String adminName, String adminEmail, String adminPassword, MultipartFile adminIcon, String confirmPassword) throws IOException {
        // 管理者が存在しない場合のみ保存を行う
        if (!emailExists(adminEmail)) {
            String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminIcon.getOriginalFilename();
            Files.copy(adminIcon.getInputStream(), Path.of("src/main/resources/static/uploads/" + fileName));

            String iconPath = "upload/" + fileName; // 这里保存的是文件路径，而不是 MultipartFile 对象
            adminDao.save(new Admin(adminName, adminEmail, adminPassword, iconPath, confirmPassword));
        } else {
            throw new RuntimeException("このメールアドレスは既に登録されています。");
        }
    }
 

    // 管理者のログイン処理
    public Admin loginAdmin(String adminEmail, String adminPassword) {
        // 指定されたメールアドレスで管理者を検索し、パスワードが一致する場合はその管理者を返します
        Admin admin = adminDao.findByAdminEmail(adminEmail);
        if (admin != null && admin.getAdminPassword().equals(adminPassword)) {
            return admin;
        } else {
            // 認証に失敗した場合は null を返します
            return null;
        }
    }

    // メールアドレスの存在確認
    public boolean emailExists(String adminEmail) {
        // 指定されたメールアドレスの管理者が存在するかどうかを返します
        return adminDao.findByAdminEmail(adminEmail) != null;
    }

    // 管理者をIDで取得するメソッド
    public Admin getAdminById(Long adminId) {
        // 指定されたIDの管理者を取得し、存在しない場合は null を返します
        return adminDao.findById(adminId).orElse(null);
    }

    // 管理者情報を保存するメソッド
    public Admin saveAdmin(Admin admin) {
        return adminDao.save(admin);
    }

    // パスワード更新メソッド
    @Transactional
    public void updatePassword(Admin admin, String newPassword) {
        admin.setAdminPassword(newPassword);
        adminDao.save(admin);
    }

    // 管理者をメールで取得するメソッド
    public Admin findByAdminEmail(String adminEmail) {
        return adminDao.findByAdminEmail(adminEmail);
    }
 // 管理者の情報とアイコンを更新するメソッド
    @Transactional
    public void updateAdminInfoWithIcon(Admin admin, MultipartFile adminIcon) throws IOException {
        // 判断管理员是否存在
    	Admin existingAdmin = adminDao.findById(admin.getAdminId()).orElse(null);
         if (existingAdmin != null) {
            // 更新管理员信息
            existingAdmin.setAdminName(admin.getAdminName());
            existingAdmin.setAdminEmail(admin.getAdminEmail());
            existingAdmin.setConfirmPassword(admin.getConfirmPassword());
            
            // 更新管理员头像
            if (adminIcon != null && !adminIcon.isEmpty()) {
                // 生成文件名
                String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + adminIcon.getOriginalFilename();
                // 将头像文件保存到指定路径
                Files.copy(adminIcon.getInputStream(), Path.of("src/main/resources/static/upload/" + fileName));
                // 设置管理员头像路径
                existingAdmin.setAdminIcon("upload/" + fileName);
            }
            
            // 保存更新后的管理员信息
            adminDao.save(existingAdmin);
        } else {
            throw new RuntimeException("指定的管理员不存在");
        }
    }
  
    
}
