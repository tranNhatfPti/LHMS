document.addEventListener("DOMContentLoaded", function() {
    const stars = document.querySelectorAll(".star");
    const ratingInput = document.getElementById("ratingInput");
    let initialRating = parseInt(ratingInput.value); // Lấy giá trị rating ban đầu từ input

    // Đánh dấu số sao tương ứng với initialRating
    stars.forEach((star, index) => {
        const starRating = parseInt(star.getAttribute("data-rating"));

        if (starRating <= initialRating) {
            star.classList.add("active");
        } else {
            star.classList.remove("active");
        }
    });

    // Xử lý sự kiện click vào sao
    stars.forEach(star => {
        star.addEventListener("click", function(event) {
            const clickedStar = event.target;
            const clickedRating = parseInt(clickedStar.getAttribute("data-rating"));

            stars.forEach((star, index) => {
                const starRating = parseInt(star.getAttribute("data-rating"));

                if (starRating <= clickedRating) {
                    star.classList.add("active");
                } else {
                    star.classList.remove("active");
                }
            });

            // Cập nhật giá trị ratingInput
            ratingInput.value = clickedRating;
        });
    });

    // Xử lý submit form
    const ratingForm = document.getElementById("ratingForm");
    ratingForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(ratingForm);

        fetch(ratingForm.action, {
            method: "POST",
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            console.log("Rating sent successfully");
            // Xử lý phản hồi sau khi gửi thành công
        })
        .catch(error => {
            console.error("Error sending rating:", error);
            // Xử lý lỗi nếu có
        });
    });
});
