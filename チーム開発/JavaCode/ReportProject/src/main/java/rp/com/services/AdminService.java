package rp.com.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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

    // 保存処理(登録処理)
    // もし、findByAdminEmail == null だったら、登録処理をします
    // saveメソッドを使用して登録処理をする
    // 保存できたら true
    // そうでない場合は、保存処理失敗 false
    @Transactional
    public boolean createAdmin(String adminName, String adminEmail, String adminPassword, MultipartFile adminIcon) {
        if (adminDao.findByAdminEmail(adminEmail) == null) {
            Admin admin = new Admin();
            admin.setAdminName(adminName);
            admin.setAdminEmail(adminEmail);
            admin.setAdminPassword(adminPassword);
            // 保存头像
            if (adminIcon != null && !adminIcon.isEmpty()) {
                saveAdminIcon(admin, adminIcon);
            }
            adminDao.save(admin);
            return true;
        } else {
            return false;
        }
    }

    //ログイン処理
    //如果adminEmail和adminPassword存在，返回Admin对象；否则返回null
    public Admin loginAdmin(String adminEmail, String adminPassword) {
        return adminDao.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
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

            // 设置管理员的头像路径
            admin.setAdminIcon(tempFile.toString());

            // 保存管理员信息
            adminDao.save(admin);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("アイコン保存が失敗しました。", e);
        }
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
            existingAdmin.setAdminPassword(admin.getAdminPassword());

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
            throw new RuntimeException("指定の管理者は存在しません");
        }
    }

    // 新增的方法来处理保存管理员信息和头像
    @Transactional
    public void saveAdminWithIcon(String adminName, String adminEmail, String adminPassword, MultipartFile adminIcon, String confirmPassword) throws IOException {
        if (!adminPassword.equals(confirmPassword)) {
            throw new RuntimeException("パスワードが一致しません");
        }

        if (emailExists(adminEmail)) {
            throw new RuntimeException("このメールアドレスは既に登録されています。");
        }

        Admin admin = new Admin();
        admin.setAdminName(adminName);
        admin.setAdminEmail(adminEmail);
        admin.setAdminPassword(adminPassword);
        if (adminIcon != null && !adminIcon.isEmpty()) {
            saveAdminIcon(admin, adminIcon);
        }
        adminDao.save(admin);
    }
}