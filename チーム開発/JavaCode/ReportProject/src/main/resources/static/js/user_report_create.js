document.addEventListener("DOMContentLoaded", function() {
    // Debugging: Check if the create button element is found
    const createButton = document.getElementById("report-create-button");
    if (createButton) {
        createButton.addEventListener("click", function() {
            window.location.href = "/user/report/create";
        });
    } else {
        console.error("#report-create-button not found.");
    }

    // Ensure the form element exists before adding an event listener
    const form = document.getElementById("report-form");
    if (form) {
        form.addEventListener("submit", function(event) {
            event.preventDefault(); // Prevent default form submission behavior

            const formData = new FormData(form);

            fetch(form.action, {
                method: form.method,
                body: formData,
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    console.error("Submission failed");
                }
            })
            .then(data => {
                if (data && data.reports) {
                    // Redirect to /user/report_list if submission is successful
                    window.location.href = "/user/report/list";
                }
            })
            .catch(error => {
                console.error("Error during submission:", error);
            });
        });
    } else {
        console.error("#report-form not found.");
    }

    // Handle delete buttons
    const deleteButtons = document.querySelectorAll(".delete-button");
    if (deleteButtons) {
        deleteButtons.forEach(button => {
            button.addEventListener("click", function(event) {
                event.preventDefault(); // Prevent default link behavior
                
                // Get the report ID from data attribute or any other method
                const reportId = button.dataset.reportId; // Assuming data-report-id attribute

                // Confirm deletion with user (optional)
                const confirmed = confirm("Are you sure you want to delete this report?");
                if (!confirmed) {
                    return; // Cancel deletion if not confirmed
                }

                // Perform deletion via fetch API or other AJAX method
                fetch(`/user/report/delete/${reportId}`, {
                    method: "DELETE", // Assuming DELETE method for deletion
                })
                .then(response => {
                    if (response.ok) {
                        // Optionally handle successful deletion
                        console.log("Report deleted successfully");
                        // Reload or redirect to update UI
                        window.location.reload(); // Reload current page
                    } else {
                        console.error("Deletion failed");
                    }
                })
                .catch(error => {
                    console.error("Error during deletion:", error);
                });
            });
        });
    } else {
        console.error(".delete-button not found.");
    }
});
