package rp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rp.com.models.dao.UsersDao;
import rp.com.models.entity.Users;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersDao usersDao;
    
    // すべてのユーザーを取得するメソッド
    public List<Users> getAllUsers() {
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
    // もし、findByUserEmail == nullだったら登録処理を行う
    // 保存ができたらtrue、そうでない場合、保存処理失敗falseを返す
    public boolean createUser(Users user) {
        if (usersDao.findByUserEmail(user.getUserEmail()) == null) {
            usersDao.save(user);
            return true;
        } else {
            return false;
        }
    }

    // ログイン処理
    // もし、emailとpasswordがfindByUserAndPasswordを使用して存在しなかった場合==nullの場合、
    // 存在しないnullであることをコントローラークラスに知らせる
    // そうでない場合、ログインしている人の情報をコントローラークラスに渡す
    public Users loginCheck(String userEmail, String userPassword) {
        Users users = usersDao.findByUserEmailAndUserPassword(userEmail, userPassword);
        return users;
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
}