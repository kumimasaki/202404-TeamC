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
