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

    function handleFileInputChange(event) {
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
    }

    const adminIconInput = document.getElementById('adminIcon');
    if (adminIconInput) {
        adminIconInput.addEventListener('change', handleFileInputChange);
    } else {
        console.error('Admin icon input not found.');
    }
});

function cancelChanges() {
    if (confirm('変更を保存せずに戻りますか？')) {
        window.location.href = 'admin_reports.html'; 
    }
}

function validateForm() {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const newPassword = document.getElementById('newPassword').value;

    if (!username || !email || !newPassword) {
        alert('ユーザー名、ユーザーメール、新しいパスワードは必須項目です。');
        return false;
    }


    return true;
}

function updateAdmin() {
    if (validateForm()) {
        document.getElementById('adminForm').submit();
    }
}
