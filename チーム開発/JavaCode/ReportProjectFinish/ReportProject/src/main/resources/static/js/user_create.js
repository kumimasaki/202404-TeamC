document.addEventListener("DOMContentLoaded", function() {
    const togglePasswordButton = document.querySelector(".toggle-password");
    if (togglePasswordButton) {
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
    } else {
        console.error('Toggle password button not found.');
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const userIconInput = document.getElementById('userIcon');
    if (userIconInput) {
        userIconInput.addEventListener('change', function(event) {
            const input = event.target;
            if (input.files && input.files[0]) {
                const file = input.files[0];
                const reader = new FileReader();

                reader.onload = function() {
                    const imageDataUrl = reader.result;
                    const iconPlaceholder = document.getElementById('icon-placeholder');
                    iconPlaceholder.src = imageDataUrl;
                };

                reader.readAsDataURL(file);
            }
        });
    } else {
        console.error('User icon input not found.');
    }
});

function cancelChanges() {
    if (confirm('変更を保存せずに戻りますか？')) {
        window.location.href = '/admin/report/list';
    }
}

function validateForm() {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!username || !email || !password) {
        alert('ユーザー名、ユーザーメール、パスワードは必須項目です。');
        return false;
    }

    return true;
}

function updateAdmin() {
    if (validateForm()) {
        document.getElementById('adminForm').submit();
    }
}
