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

function previewIcon(event) {
    const input = event.target;
    const reader = new FileReader();
    reader.onload = function() {
        const iconPlaceholder = document.getElementById('icon-placeholder');
        const iconImg = document.createElement('img');
        iconImg.src = reader.result;
        iconPlaceholder.innerHTML = '';
        iconPlaceholder.appendChild(iconImg);
        console.log(reader.result);
    };
    reader.readAsDataURL(input.files[0]);
}

  // パスワード確認のチェック
    if (password !== confirmPassword) {
        alert('パスワードが一致しません。');
        return;
    }
