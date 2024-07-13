<%-- 
    Document   : payment
    Created on : Jun 14, 2024, 10:26:38 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.ServletContext, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession, java.util.List, model.Bill, dal.BillDAO, dal.RoomDAO, dal.ServiceOfRoomDAO, model.ServiceOfRoom, dal.ServiceDAO, dal.AccountDAO, dal.AccountInRoomDAO"%>
<%@page import="model.Contract"%>
<%@page import="dal.ContractDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Thanh toán hoá đơn</title>
        <!-- Custom styles for this template -->    
        <script src="/ManageLodgingHouse/Resource/assets/jquery-1.11.3.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <style>
            :root {
                --color-background: #fae3ea;
                --color-primary: #fc8080;
                --font-family-base: Poppin, sans-serif;
                --font-size-h1: 1.25rem;
                --font-size-h2: 1rem;
            }

            * {
                box-sizing: inherit;
            }

            html {
                box-sizing: border-box;
            }

            body {
                background-color: #d7d7d7;
                display: grid;
                font-family: var(--font-family-base);
                line-height: 1.5;
                margin: 0;
                min-block-size: 100vh;
                padding: 5vmin;
                place-items: center;
            }

            address {
                font-style: normal;
            }

            button {
                border: 0;
                color: inherit;
                cursor: pointer;
                font: inherit;
            }

            fieldset {
                border: 0;
                margin: 0;
                padding: 0;
            }

            h1 {
                font-size: var(--font-size-h1);
                line-height: 1.2;
                margin-block: 0 1.5em;
            }

            h2 {
                font-size: var(--font-size-h2);
                line-height: 1.2;
                margin-block: 0 0.5em;
            }

            legend {
                font-weight: 600;
                margin-block-end: 0.5em;
                padding: 0;
            }

            input {
                border: 0;
                color: inherit;
                font: inherit;
            }

            input[type="radio"] {
                accent-color: var(--color-primary);
            }

            table {
                border-collapse: collapse;
                inline-size: 100%;
            }

            tbody {
                color: black;
            }

            td {
                padding-block: 0.125em;
            }

            tr{
                line-height: 50px;
            }

            tfoot {
                border-top: 1px solid #b4b4b4;
                font-weight: 600;
            }

            .align {
                display: grid;
                place-items: center;
            }

            .button {
                align-items: center;
                background-color: var(--color-primary);
                border-radius: 999em;
                color: #fff;
                display: flex;
                gap: 0.5em;
                justify-content: center;
                padding-block: 0.75em;
                padding-inline: 1em;
                transition: 0.3s;
            }

            .button:focus,
            .button:hover {
                background-color: #e96363;
            }

            .button--full {
                inline-size: 100%;
            }

            .button--30 {
                inline-size: 30%;
            }

            .card {
                border-radius: 1em;
                background-color: var(--color-primary);
                color: #fff;
                padding: 1em;
            }

            .form {
                display: grid;
                gap: 2em;
            }

            .form__radios {
                display: grid;
                gap: 1em;
            }

            .form__radio {
                align-items: center;
                background-color: #fefdfe;
                border-radius: 1em;
                padding: 1em;
            }

            .form__radio label {
                align-items: center;
                display: flex;
                flex: 1;
                gap: 1em;
            }

            .header {
                display: flex;
                justify-content: center;
                padding-block: 0.5em;
                padding-inline: 1em;
            }

            .icon {
                block-size: 1em;
                display: inline-block;
                fill: currentColor;
                inline-size: 1em;
                vertical-align: middle;
            }

            .iphone {
                background-color: #fbf6f7;
                background-image: linear-gradient(to bottom, #fbf6f7, #fff);
                border-radius: 2em;
                block-size: 812px;
                box-shadow: 0 0 1em rgba(0, 0, 0, 0.0625);
                inline-size: 690px;
                overflow: auto;
                padding: 2em;
            }

            .price {
                color: dodgerblue;
            }
        </style>
    </head>

    <body>
        <%
            BillDAO bd = new BillDAO();
            RoomDAO rd = new RoomDAO();
            ServiceDAO sd = new ServiceDAO();
            AccountDAO ad = new AccountDAO();
            ContractDAO cd = new ContractDAO();
            AccountInRoomDAO ard = new AccountInRoomDAO();
            InformationOfUserDAO iud = new InformationOfUserDAO();
            ServiceOfRoomDAO srd = new ServiceOfRoomDAO();
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            
            Bill billNeedPay = (Bill) request.getAttribute("billNeedPay");
        %>
        <div style="display: flex">
            <div class="iphone" style="margin-right: 20px">
                <header class="header">
                    <h1>Thông tin khác</h1>
                </header>

                <form action="https://httpbin.org/post" class="form" method="POST">
                    <fieldset >
                        <legend>Thông tin người thuê</legend>
                        <legend><a href="cost-incurred?service=showListCost&index=1&pay=all">Back</a></legend>
                        <div class="form__radios" style="margin-top: 5px">
                            <div class="form__radio" style="box-shadow: 0 0 1em #8f8080; display: inline-flex">
                                <div style="font-family: math; color: #5d5d5d">
                                    <label>Họ và tên: </label>
                                    <label style="margin: 10px 0">Email: </label>
                                    <label style="margin-bottom: 10px">Số điện thoại: </label>
                                    <label>Ở phòng: </label>
                                </div>
                                <div style="margin-left: 50px">
                                    <div>
                                        <strong>${userInfor.fullName}</strong>
                                    </div>
                                    <div style="margin: 10px 0">  
                                        <strong>${account.email}</strong>
                                    </div>
                                    <div style="margin-bottom: 10px">           
                                        <strong>${userInfor.phoneNumber}</strong>
                                    </div>
                                    <div>           
                                        <strong>1</strong>
                                    </div>
                                </div>
                            </div>          
                        </div>
                    </fieldset>

                    <fieldset style="margin: 15px 0">
                        <legend>Phương thức thanh toán</legend>
                        <div class="form__radios" style="margin-top: 5px; width: 50%">
                            <div class="form__radio" style="box-shadow: 0 0 1em #14842f; padding: 25px 0">
                                <label for="visa"><svg class="icon">
                                    <img alt="logo" sizes="10vw" srcset="https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 640w, 
                                         https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 750w, 
                                         https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 828w, 
                                         https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 1080w, 
                                         https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 1200w, 
                                         https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 1920w, 
                                         https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 2048w, 
                                         https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg 3840w" 
                                         src="https://stcd02206177151.cloud.edgevnpay.vn/assets/images/logo-icon/logo.svg" 
                                         decoding="async" data-nimg="fill" class="image" loading="lazy" style="width: 100px">
                                    </div>          
                                    </div>
                                    </fieldset>

                                    <fieldset>
                                        <legend><i class="fas fa-info-circle" style="margin-right: 8px;"></i>Hướng dẫn thanh toán</legend>
                                        <strong style="color: red; font-size: 15px">- Vui lòng xem xét lại các khoản tiền đã chính xác hay chưa trước khi chuyển tiền.</strong><br>
                                        <strong style="color: red; font-size: 15px">- Sau khi kiểm tra các khoản tiền hãy bấm vào thanh toán và chọn cách thanh toán.</strong><br>
                                        <strong style="color: red; font-size: 15px">- Sau khi chuyển tiền xong hãy kiểm tra lại trạng thái thanh toán trong hoá đơn.</strong>
                                    </fieldset>
                                    </form>
                            </div>

                            <!-- --------------------------------------------------------------------------------------------------- -->

                            <div class="iphone" style="margin-left: 20px">
                                <header class="header">
                                    <h1>Thông tin hoá đơn</h1>
                                </header>
                                <form action="cost-incurred?service=update&id=${receipt.receiptId}" class="form" id="frmCreateOrder" method="post">        
                                    <div>
                                        <h2>Hóa Đơn Thanh Toán</h2>
                                        <hr>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>Mã Hóa Đơn</td>
                                                    <td align="right" class="price">${receipt.receiptId}</td>
                                                </tr>
                                                <tr>
                                                    <td>Mô Tả</td>
                                                    <td align="right" class="price">${receipt.description}</td>
                                                </tr>
                                                <tr>
                                                    <td>Thời Gian</td>
                                                    <td align="right" class="price">${receipt.dateTime}</td>
                                                </tr>
                                                <tr>
                                                    <td>Tiền</td>
                                                    <td align="right" class="price">${receipt.price}</td>
                                                </tr>
                                            </tbody>                        
                                        </table>
                                        <hr>
                                        <div style="background-color: #ffdbdb; display: flex; justify-content: space-between; padding: 20px 15px">
                                            <strong>Tổng tiền thanh toán</strong>
                                            <strong align="right" style="color: crimson">${receipt.price}</strong>
                                        </div>
                                    </div>
                                    <div style="display: flex; justify-content: center">
                                        <button class="button button--30" type="submit" id="btnSubmit">Thanh toán</button>
                                        
                                    </div>
                                </form>

                            </div>
                        </div>
                        <link href="https://pay.vnpay.vn/lib/vnpay/vnpay.css" rel="stylesheet"/>
                        <script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>
                        <script type="text/javascript">
                            $("#frmCreateOrder").submit(function () {
                                var postData = $("#frmCreateOrder").serialize();
                                var submitUrl = $("#frmCreateOrder").attr("action");
                                $.ajax({
                                    type: "POST",
                                    url: submitUrl,
                                    data: postData,
                                    dataType: 'JSON',
                                    success: function (x) {
                                        if (x.code === '00') {
                                            if (window.vnpay) {
                                                //                                console.log(x.data);
                                                //                                console.log(x.text);
                                                vnpay.open({width: 768, height: 600, url: x.data});
                                            } else {
                                                location.href = x.data;
                                            }
                                            return false;
                                        } else {
                                            alert(x.Message);
                                        }
                                    }
                                });
                                return false;
                            });

                            function printBill() {
                                window.print();
                            }
                        </script>
                        <script>
                            document.getElementById('btnSubmit').addEventListener('click', function () {
                                document.getElementById('frmCreateOrder').submit();
                            });
                        </script>
                        </body>
                        </html>
