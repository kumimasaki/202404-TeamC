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
                        const iconPlaceholder = document.getElementById('icon-placeholder');
                        iconPlaceholder.src = imageDataUrl;
                    };

                    reader.readAsDataURL(file);
                }
            });

   
 
    window.cancelChanges = function() {
        if (confirm('変更を保存せずに戻りますか？')) {
            window.location.href = '/admin/report/list'; 
        }
    };
});
