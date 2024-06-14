
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

