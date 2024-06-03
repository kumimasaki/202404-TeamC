document.getElementById('adminForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    // フォームデータの取得
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

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
