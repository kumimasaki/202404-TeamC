document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("report-create-button").addEventListener("click", function() {
        window.location.href = "/user/report/create";
    });
});

document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("report-form");

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(form);

        fetch(form.action, {
            method: form.method,
            body: formData,
        })
        .then(response => {
            if (response.ok) {
                // 如果提交成功，重定向到 /user/report_list
                window.location.href = "/user/report_list";
            } else {
                // 处理提交失败的情况
                console.error("提交失败");
            }
        })
        .catch(error => {
            console.error("提交时发生错误:", error);
        });
    });
});
