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

    document.getElementById('adminIcon').addEventListener('change', function(event) {
        const input = event.target;
        if (input.files && input.files[0]) {
            const file = input.files[0];
            const reader = new FileReader();

            reader.onload = function() {
                const imageDataUrl = reader.result;
                const iconCircle = document.querySelector('.icon-placeholder');
                iconCircle.style.backgroundImage = `url(${imageDataUrl})`;
                iconCircle.style.backgroundSize = 'cover';
                iconCircle.style.backgroundPosition = 'center';
            };

            reader.readAsDataURL(file);
        }
    });

    // パスワード確認のチェック
    const password = document.querySelector('[name="adminPassword"]').value;
    const confirmPassword = document.querySelector('[name="confirmPassword"]').value;
    if (password !== confirmPassword) {
        alert('パスワードが一致しません。');
        return false;
    }
});

