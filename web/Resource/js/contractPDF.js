/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 * 
 * 
 */
             // Đăng ký sự kiện khi tài liệu đã tải xong
        window.onload = function () {
            // Đăng ký sự kiện khi nút "In ra PDF" được click
            document.getElementById("download").addEventListener("click", function () {
                // Lấy nội dung HTML của modal-body
                const modalBody = document.getElementById("modalBody").innerHTML;

                // Tạo tùy chọn cho html2pdf
                var opt = {
                    margin: 1,
                    filename: 'myfile.pdf',
                    image: {type: 'jpeg', quality: 0.98},
                    html2canvas: {scale: 2},
                    jsPDF: {unit: 'in', format: 'letter', orientation: 'portrait'}
                };

                // Sử dụng html2pdf để tạo và lưu trữ PDF từ nội dung của modal
                html2pdf().from(modalBody).set(opt).save();
            });
        }
        

