package rp.com.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
    public Admin registerAdmin(Admin admin) {
        // 指定されたメールアドレスの管理者が存在しない場合、新しい管理者を作成します
        if (adminDao.findByAdminEmail(admin.getAdminEmail()) == null) {
            // 新しい管理者を保存します
            return adminDao.save(admin);
        } else {
            // 既に管理者が存在する場合は null を返します
            return null;
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

    // iconを保存するメソッド
    public void saveAdminIcon(Admin admin, MultipartFile adminIconFile) {
        try {
            // 创建临时文件并复制头像文件
            Path tempFile = Files.createTempFile("temp", adminIconFile.getOriginalFilename());
            Files.copy(adminIconFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // 设置管理员的头像
            admin.setAdminIcon(adminIconFile);

            // 保存管理员信息
            adminDao.save(admin);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("アイコン保存が失敗しました。", e);
        }
    }
}