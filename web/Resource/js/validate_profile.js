function validateForm() {
    var isValid = true;

    // Reset error messages
    var errorElements = document.getElementsByClassName("error");
    for (var i = 0; i < errorElements.length; i++) {
        errorElements[i].innerText = "";
    }

    // Validate Full Name
    var regex = /\d/;
    var fullName = document.forms["registrationForm"]["fullName"].value;
    if (fullName === "" || fullName.length > 30 || regex.test(fullName)) {
        document.getElementById("fullNameError").innerText = "*Họ và Tên không được để trống và không được chứa số*";
        isValid = false;
    } else {
        document.getElementById("fullNameError").innerText = " ";
    }


    // Validate Phone
    var phone = document.forms["registrationForm"]["phone"].value;
    var phonePattern = /^[0-9]{10}$/;
    if (phone === "" || !phonePattern.test(phone)) {
        document.getElementById("phoneError").innerText = "*Số điện thoại không hợp lệ.*";
        isValid = false;
    } else {
        document.getElementById("phoneError").innerText = " ";
    }
    // Validate Dob
    var dob = document.forms["registrationForm"]["dob"].value;
    if (dob === "") {
        document.getElementById("dobError").innerText = "*Ngày sinh không hợp lệ*";
        isValid = false;
    } else {
        document.getElementById("dobError").innerText = " ";
    }

    // Validate Address Detail
    var addressDetail = document.forms["registrationForm"]["address"].value;
    if (addressDetail.length > 100 || addressDetail.length==="" ) {
        document.getElementById("addressError").innerText = "*Địa Chỉ Cụ Thể không được để trống hoặc quá 100 kí tự.*";
        isValid = false;
    } else {
        document.getElementById("addressError").innerText = " ";
    }

    // Validate CIC
    var cic = document.forms["registrationForm"]["cic"].value;
    var cicPattern = /^[0-9]{12}$/;
    if (cic === "" || !cicPattern.test(cic)) {
        document.getElementById("cicError").innerText = "*Số căn cước công dân không hợp lệ.*";
        isValid = false;
    } else {
        document.getElementById("cicError").innerText = " ";
    }

    // Validate province
    const province = document.getElementById('tinh').value;
    const provinceError = document.getElementById('provinceError');
    if (province === "0") {
        provinceError.textContent = "*Vui lòng chọn Tỉnh Thành.*";
        isValid = false;
    } else {
        provinceError.textContent = "";
    }

    // Validate district
    const district = document.getElementById('quan').value;
    const districtError = document.getElementById('districtError');
    if (district === "0") {
        districtError.textContent = "*Vui lòng chọn Quận Huyện.*";
        isValid = false;
    } else {
        districtError.textContent = "";
    }

    // Validate ward
    const ward = document.getElementById('phuong').value;
    const wardError = document.getElementById('wardError');
    if (ward === "0") {
        wardError.textContent = "*Vui lòng chọn Phường Xã.*";
        isValid = false;
    } else {
        wardError.textContent = "";
    }
    const service = document.getElementById('service').value;
    if (isValid) {
        if(service==="Yêu Cầu Cập Nhật Thông Tin Thành Công"){
           alert("CậpThông Tin Thành Cônsdfsfsdfg!");
           document.getElementById("registrationForm").submit(); 
        }else{
           alert("Cập Nhật Thông Tin Thành Công !");
           document.getElementById("registrationForm").submit();
        }
    }

    return false; // Ngăn form submit mặc định
}