package rp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import rp.com.models.dao.UsersDao;
import rp.com.models.entity.Admin;
import rp.com.models.entity.Users;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private AdminService adminService;

    // すべてのユーザーを取得するメソッド
    public List<Users> getAllUserList() {
        return usersDao.findAll();
    }

    // ユーザー名またはメールアドレスでユーザーを検索するメソッド
    public List<Users> searchUsersByNameOrEmail(String search) {
        return usersDao.findByUserNameContainingOrUserEmailContaining(search, search);
    }

    // ユーザーを削除するメソッド
    public void deleteUser(Long userId) {
        usersDao.deleteById(userId);
    }

    // ユーザーの作成処理
    public void saveUserWithIcon(String userName, String userEmail, String userPassword, MultipartFile userIcon, Long adminId) throws IOException {
        // ユーザーが存在しない場合のみ保存を行う
        if (!emailExists(userEmail)) {
            String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + userIcon.getOriginalFilename();
            Files.copy(userIcon.getInputStream(), Path.of("src/main/resources/static/uploads/" + fileName));

            String userIconPath  = "uploads/" + fileName;
            
            Admin admin = adminService.getAdminById(adminId); 
            usersDao.save(new Users(userName, userEmail, userPassword, LocalDateTime.now(), userIconPath , admin));
        } else {
            throw new RuntimeException("このメールアドレスは既に登録されています。");
        }
    }



    // メールアドレスの存在確認
    public boolean emailExists(String userEmail) {
        // 指定されたメールアドレスのユーザーが存在するかどうかを返します
        return usersDao.findByUserEmail(userEmail) != null;
    }

    // ログイン処理
    // もし、emailとpasswordがfindByUserAndPasswordを使用して存在しなかった場合==nullの場合、
    // 存在しないnullであることをコントローラークラスに知らせる
    // そうでない場合、ログインしている人の情報をコントローラークラスに渡す
    public Users loginCheck(String userEmail, String userPassword) {
        Users users = usersDao.findByUserEmailAndUserPassword(userEmail, userPassword);
        if (users == null) {
            return null;
        } else {
            return users;
        }
    }

    // ユーザーIDでユーザーを取得するメソッド
    public Users getUserById(Long userId) {
        Optional<Users> user = usersDao.findById(userId);
        return user.orElse(null);
    }

    // ユーザーを更新するメソッド
    public void updateUser(Users user) {
        usersDao.save(user);
    }

    // ユーザーのアイコンパスを取得するメソッド
    public String getIconPathForUser(Long userId) {
        Optional<Users> optionalUser = usersDao.findById(userId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            return user.getUserIcon();
        } else {
            return null;
        }
    }
}