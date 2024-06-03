document.getElementById('adminForm').addEventListener('submit', function(event) {
    event.preventDefault();
    alert('Form submitted!');
});

function cancelChanges() {
    if (confirm('変更を保存せずに戻りますか？')) {
        window.location.href = 'index.html'; // Change to your home page URL
    }
}
