document.getElementById('adminForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    // フォームデータの取得
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const userIcon = document.getElementById('userIcon').files[0];
    const formData = new FormData();
    
      formData.append('username', username);
            formData.append('email', email);
             formData.append('userIcon', userIcon);
             
             fetch('http://localhost:8080', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // 处理服务器响应
                console.log(data);
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    
    // パスワード確認のチェック
    if (password !== confirmPassword) {
        alert('パスワードが一致しません。');
        return;
    }

    // ここにフォームデータの送信ロジックを追加します
    alert('ユーザーが追加されました！');

    // 送信後にフォームをリセットする場合
    document.getElementById('adminForm').reset();
});

function cancelChanges() {
    if (confirm('変更を保存せずに戻りますか？')) {
        window.location.href = 'index.html'; // ホームページのURLに変更
    }
}
document.addEventListener("DOMContentLoaded", function() {
    const togglePasswordButton = document.querySelector(".toggle-password");
    const passwordField = togglePasswordButton.previousElementSibling;
    const icon = togglePasswordButton.querySelector('i');

    togglePasswordButton.addEventListener("click", function() {
        if (passwordField.type === "password") {
            passwordField.type = "text";
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        } else {
            passwordField.type = "password";
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        }
    });
});
document.getElementById('userIcon').addEventListener('change', function(event) {
    const input = event.target;
    const file = input.files[0];
    const reader = new FileReader();

    reader.onload = function() {
        const imageDataUrl = reader.result;
        const iconCircle = document.querySelector('.icon-circle');
        iconCircle.style.backgroundImage = `url(${imageDataUrl})`;
        iconCircle.style.backgroundSize = 'cover';
        iconCircle.style.backgroundPosition = 'center';
    };

    reader.readAsDataURL(file);
});

