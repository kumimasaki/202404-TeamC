document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("report-create-button").addEventListener("click", function() {
                window.location.href = "/user/report/create"; // Change this URL to your report creation page URL
            });
        });

// Handle delete buttons
const deleteButtons = document.querySelectorAll(".delete-button");
deleteButtons.forEach(button => {
    button.addEventListener("click", function(event) {
        event.preventDefault(); // Prevent default form submission behavior

        const form = button.closest("form");
        const formData = new FormData(form);

        fetch(form.action, {
            method: form.method,
            body: formData,
        })
        .then(response => response.json())
        .then(data => {
            // Reload the page to reflect changes
            window.location.reload();
        })
        .catch(error => {
            console.error("Error during delete action:", error);
            // Reload the page even if there is an error
            window.location.reload();
        });
    });
});
