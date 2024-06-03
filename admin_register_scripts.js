document.addEventListener("DOMContentLoaded", function() {
    const togglePasswordButton = document.querySelector(".toggle-password");
    const passwordField = togglePasswordButton.previousElementSibling;

    togglePasswordButton.addEventListener("click", function() {
        if (passwordField.type === "password") {
            passwordField.type = "text";
        } else {
            passwordField.type = "password";
        }
    });
});

function previewIcon(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const iconPlaceholder = document.getElementById('icon-placeholder');
        const iconImg = document.createElement('img');
        iconImg.src = reader.result;
        iconPlaceholder.innerHTML = '';
        iconPlaceholder.appendChild(iconImg);
    };
    reader.readAsDataURL(event.target.files[0]);
}
