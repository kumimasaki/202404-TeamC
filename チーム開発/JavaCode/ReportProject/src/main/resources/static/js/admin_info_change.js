document.getElementById('adminForm').addEventListener('submit', function(event) {
   
});

function cancelChanges() {
    if (confirm('変更を保存せずに戻りますか？')) {
       window.location.href = '/admin/report/list'; 
    }
}
