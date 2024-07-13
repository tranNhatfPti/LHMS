var accountId = null; // sender
var websocket = null;
var receiver = null;
var userAvatar = null;
var receiverAvatar = null;

var groupName = null;
var groupId = null

var back = null;
var rightSide = null;
var leftSide = null;
var conversation = null;

var attachFile = null;
var imageFile = null;
var file = null;
var listFile = []; // mảng file đã chọn trong thẻ input
var typeFile = "image";
var deleteAttach = null;

var typeChat = "user";

var listUserAdd = [];
var listUserDelete = [];
var numberMember = 0;

window.onload = function () {
    if ("WebSocket" in window) {
        accountId = document.getElementById("username").textContent;
        userAvatar = document.getElementById("userAvatar").textContent;

        // khi user online thì sẽ tự động kết nối tới socket server và gửi theo 1 param là accountId
        websocket = new WebSocket('ws://' + window.location.host + '/ManageLodgingHouse/chat/' + accountId);

        websocket.onopen = function () {
        };

        websocket.onmessage = function (data) {
            setMessage(JSON.parse(data.data));
        };

        websocket.onerror = function () {
            console.log('An error occured, closing application');
            cleanUp();
        };

        websocket.onclose = function (data) {
            console.log(data.reason);
            cleanUp();
        };
    } else {
        console.log("Websockets not supported");
    }
}

window.onclick = function (e) {
    let modals = document.querySelectorAll(".modal-box");
    let toggleBtns = document.querySelectorAll(".toggle-btn");
    let count = 0;

    modals.forEach(function (modal) {
        if (modal.contains(e.target))
            count++;
    });

    toggleBtns.forEach(function (toggleBtn) {
        if (toggleBtn.contains(e.target))
            count++;
    });

    if (count !== 1)
        toggleAllModal();
}


function cleanUp() {
    username = null;
    websocket = null;
    receiver = null;
}

function setReceiver(element) {
    groupId = null;
    receiver = element.id;
    receiverAvatar = document.getElementById('img-' + receiver).src;
    var status = '';
    if (document.getElementById('status-' + receiver).classList.contains('online')) {
        status = 'online';
    }
    var rightSide = '<div class="user-contact">' + '<div class="back">'
            + '<i class="fa fa-arrow-left"></i>'
            + '</div>'
            + '<div class="user-contain">'
            + '<div class="user-img">'
            + '<img src="' + receiverAvatar + '" '
            + 'alt="Image of user">'
            + '<div class="user-img-dot ' + status + '"></div>'
            + '</div>'
            + '<div class="user-info">'
            + '<span id="user-name" class="user-name">'
            + receiver
            + '</span>'
            + '</div>'
            + '</div>'
            + '<div class="setting">'
            + '<i class="fa fa-cog"></i>'
            + '</div>'
            + '</div>'
            + '<div class="list-messages-contain">'
            + '<ul id="chat" class="list-messages">'
            + '</ul>'
            + '</div>'
            + '<form class="form-send-message" onsubmit="return sendMessage(event)">'
            + '<ul class="list-file"></ul> '
            + '<input type="text" id="message" class="txt-input" placeholder="Nhập tin nhắn...">'
            + '<label class="btn btn-image" for="attach"><i class="fa fa-file"></i></label>'
            // cho phép chọn nhiều file từ máy của bạn(bao gồm cả ảnh)
            + '<input type="file" multiple id="attach">'
            + '<label class="btn btn-image" for="image"><i class="fa fa-file-image-o"></i></label>'
            // cho phép bạn chọn nhiều tệp ảnh(ko được chọn các loại tệp khác), bao gồm các loại tệp ảnh: jpg, png,...
            + '<input type="file" accept="image/*" multiple id="image">'
            + '<button type="submit" class="btn btn-send">'
            + '<i class="fa fa-paper-plane"></i>'
            + '</button>'
            + '</form>';

    // set html và css cho right side
    document.getElementById("receiver").innerHTML = rightSide;

    loadMessages();

    displayFiles();

    makeFriend(rightSide);

    displayFullName(receiver);
}

// sửa lại image message
function setStyleImageFile() {
    var listMessage = document.querySelectorAll(".message-text");
    console.log(listMessage.length);
    listMessage.forEach(function (ms) {
        const imgEle = ms.querySelector('img');
        let srcImage = "";
        if (imgEle) {
            srcImage = imgEle.src;

            imgEle.style.visibility = 'hidden';
            ms.style.backgroundImage = "url('" + srcImage + "')";
            ms.style.backgroundSize = "cover";  // Điều chỉnh kích thước ảnh để che phủ toàn bộ div
            ms.style.backgroundPosition = "center";  // Đặt ảnh ở giữa div
            ms.style.backgroundRepeat = "no-repeat";  // Không lặp lại ảnh
            ms.style.borderRadius = '20px';
            ms.style.cursor = "pointer";
        }
    });
}

function setGroup(element) {
    receiver = null;
    groupName = element.getAttribute("data-name");
    groupId = element.getAttribute("data-id");

    receiverAvatar = document.getElementById("img-group-" + groupId).src;

    listUserAdd = [];

    numberMember = parseInt(element.getAttribute("data-number"));


    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller?usersConversationId=" + groupId)
            .then(data => data.json())
            .then(data => {
                let findObject = data.find(element => element.username == username);
                let isAdmin = findObject.admin;

                var rightSide = '<div class="user-contact">' + '<div class="back">'
                        + '<i class="fa fa-arrow-left"></i>'
                        + '</div>'
                        + '<div class="user-contain">'
                        + '<div class="user-img">'
                        + '<img id="img-group-' + groupId + '" src="' + receiverAvatar + '"'
                        + 'alt="Image of user">'
                        + '</div>'
                        + '<div class="user-info">'
                        + '<a href="http://' + window.location.host + '/ManageLodgingHouse/conversation?conversationId=' + groupId + '" class="user-name">' + groupName + '</a>'
                        + '</div>'
                        + '</div>'
                        + '<div class="invite-user">'
                        + '<span class="total-invite-user">' + numberMember + ' paticipants</span>'
                        + '<span data-id="add-user" onclick="toggleModal(this, true); searchMemberByKeyword(``);" class="invite toggle-btn">Invite</span>'
                        + '</div>'
                        + '<div class="setting toggle-btn" data-id="manage-user" onclick="toggleModal(this, true);  fetchUser();">'
                        + '<i class="fa fa-cog"></i>'
                        + '</div>'
                        + '</div>'
                        + '<div class="list-messages-contain">'
                        + '<ul id="chat" class="list-messages">'
                        + '</ul>'
                        + '</div>'
                        + '<form class="form-send-message" onsubmit="return sendMessage(event)">'
                        + '<ul class="list-file"></ul> '
                        + '<input type="text" id="message" class="txt-input" placeholder="Type message...">'
                        + '<label class="btn btn-image" for="attach"><i class="fa fa-file"></i></label>'
                        + '<input type="file" multiple id="attach">'
                        + '<label class="btn btn-image" for="image"><i class="fa fa-file-image-o"></i></label>'
                        + '<input type="file" accept="image/*" multiple id="image">'
                        + '<button type="submit" class="btn btn-send">'
                        + '<i class="fa fa-paper-plane"></i>'
                        + '</button>'
                        + '</form>';

                document.getElementById("receiver").innerHTML = rightSide;

                loadMessagesGroup();

                displayFiles();

                handleResponsive();
            })
            .catch(ex => console.log(ex));
}

function resetChat() {
    let chatBtn = document.querySelectorAll(".tab-control i");
    let searchTxt = document.querySelector(".list-user-search input");
    let addGroupBtn = document.querySelector(".add-group");

    searchTxt.value = "";

    chatBtn.forEach(function (ele) {
        ele.classList.remove("active");
    });

    if (typeChat == "group") {
        addGroupBtn.classList.add("active");
    } else {
        addGroupBtn.classList.remove("active");
    }
}


function createGroup(e) {
    e.preventDefault();

    let groupName = document.querySelector(".txt-group-name").value;

    let object = new Object();
    let user = new Object();

    user.username = username;
    user.admin = true;

    object.name = groupName;
    object.users = [];
    object.users.push(user);
    toggleAllModal();

    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller", {
        method: "post",
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(object)
    })
            .then(function (data) {
                return data.json();
            })
            .then(function (data) {

                if (typeChat != "group")
                    return;

                let numberMember = data.users.length;

                let imgSrc = ' src="http://' + window.location.host + '/ManageLodgingHouse/files/group-' + data.id + '/' + data.avatar + '"';
                let appendUser = '<li id="group-' + data.id + '">'
                        + '<div class="user-contain" data-id="' + data.id + '" data-number="' + numberMember + '" data-name="' + data.name + '" onclick="setGroup(this);">'
                        + '<div class="user-img">'
                        + '<img id="img-group-' + data.id + '"'
                        + imgSrc
                        + ' alt="Image of user">'
                        + '</div>'
                        + '<div class="user-info" style="flex-grow:1 ;">'
                        + '<span class="user-name">' + data.name + '</span>'
                        + '</div>'
                        + '</div>'
                        + '<div class="group-delete border" data-id="' + data.id + '" onclick="deleteGroup(this)">Delete</div>'
                        + '</li>';
                document.querySelector(".left-side .list-user").innerHTML += appendUser;
                document.querySelector(".txt-group-name").value = "";
            });
}

function addMember(e) {
    e.preventDefault();

    let object = new Object();
    object.name = groupName;
    object.id = groupId;
    object.users = [];


    listUserAdd.forEach(function (username) {
        let user = new Object();

        user.username = username;
        user.admin = false;
        user.avatar = null;

        object.users.push(user);
    });


    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller", {
        method: "post",
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(object)
    })
            .then(function (data) {
                return data.json();
            })
            .then(function (data) {
                numberMember += parseInt(listUserAdd.length);
                listUserAdd = [];
                let inviteNumber = document.querySelector(".total-invite-user");
                if (inviteNumber)
                    inviteNumber.innerHTML = numberMember + " paticipants";

                document.getElementById("group-" + groupId).querySelector(".user-contain").setAttribute("data-number", numberMember);

                toggleAllModal();
            });
}

function fetchUser() {

    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller?usersConversationId=" + groupId)
            .then(data => data.json())
            .then(users => {
                document.querySelector(".manage-member-body .list-user ul").innerHTML = "";

                if (users.length == 1) {
                    document.querySelector(".manage-member-body .list-user ul").innerHTML = "No members in group";
                    document.querySelector(".manage-member-body .list-user ul").style = "text-align: center; font-size: 1.8rem;";
                } else {
                    document.querySelector(".manage-member-body .list-user ul").style = "";
                }

                users.forEach(function (data) {
                    if (data.username == username)
                        return;

                    let appendUser = '<li>'
                            + '<div class="user-contain">'
                            + '<div class="user-img">'
                            + '<img '
                            + ' src="http://' + window.location.host + '/ManageLodgingHouse/files/' + data.username + '/' + data.avatar + '"'
                            + 'alt="Image of user">'
                            + '</div>'
                            + '<div class="user-info" style="flex-grow: 1;">'
                            + '<span class="user-name">' + data.username + '</span>'
                            + '</div>';

                    if (!data.admin)
                        appendUser += '<div class="user-delete" style="font-weight: 700;" data-username="' + data.username + '" onclick="deleteMember(this)">Delete</div>'

                    appendUser += '</div></li>';

                    document.querySelector(".manage-member-body .list-user ul").innerHTML += appendUser;
                });

            })
            .catch(ex => console.log(ex));

}

function deleteGroup(ele) {
    let grpId = ele.getAttribute("data-id");

    if (grpId == groupId)
        document.querySelector(".right-side").innerHTML = "";

    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller?conversationId=" + grpId, {
        method: 'delete'
    })
            .then(function (data) {
                return data.json();
            })
            .then(function (data) {

                let groupNumber = document.getElementById("group-" + grpId);
                if (groupNumber)
                    groupNumber.outerHTML = "";

            })
            .catch(ex => console.log(ex));
}

function deleteMember(ele) {
    let username = ele.getAttribute("data-username");

    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller?conversationId=" + groupId + "&username=" + username, {
        method: 'delete'
    })
            .then(function (data) {
                return data.json();
            })
            .then(function (data) {

                numberMember -= 1;

                let inviteNumber = document.querySelector(".total-invite-user");
                if (inviteNumber)
                    inviteNumber.innerHTML = numberMember + " paticipants";

                toggleAllModal();
            })
            .catch(ex => console.log(ex));

}

function toggleAllModal() {
    let modalBox = document.querySelectorAll(".modal-box");

    modalBox.forEach(function (modal) {
        modal.classList.remove("active");
    });

}

function toggleModal(ele, mode) {
    let modalBox = document.querySelectorAll(".modal-box");
    let id = ele.getAttribute("data-id");

    modalBox.forEach(function (modal) {
        modal.classList.remove("active");
    });


    if (mode)
        document.getElementById(id).classList.add("active");
    else
        document.getElementById(id).classList.remove("active");
}

function chatOne(ele) {
    typeChat = "user";
    resetChat();
    ele.classList.add("active");
    searchFriendByKeyword("");
    listFiles = [];
}

function chatGroup(ele) {
    typeChat = "group";
    resetChat();
    ele.classList.add("active");
    fetchGroup();
    listFiles = [];
}

function addUserChange(e) {
    if (e.checked) {
        listUserAdd.push(e.value);
    } else {
        let index = listUserAdd.indexOf(e.value);
        listUserAdd.splice(index, 1);
    }

}

function makeFriend(rightSide) {
    fetch("http://" + window.location.host + "/ManageLodgingHouse/friend-rest-controller?sender=" + accountId + "&receiver=" + receiver)
            .then(function (data) {
                return data.json();
            })
            .then(data => {
                var status = '';
                if (document.getElementById('status-' + receiver).classList.contains('online')) {
                    status = 'online';
                }

                var accountIdToCompare = parseInt(accountId);
                // this user gửi request kb nhưng chưa được accept
                if (data.status === false && data.owner === accountIdToCompare && data.owner !== 0) {
                    rightSide = '<div class="user-contact">' + '<div class="back">'
                            + '<i class="fa fa-arrow-left"></i>'
                            + '</div>'
                            + '<div class="user-contain">'
                            + '<div class="user-img">'
                            + '<img src="' + receiverAvatar + '" '
                            + 'alt="Image of user">'
                            + '<div class="user-img-dot ' + status + '"></div>'
                            + '</div>'
                            + '<div class="user-info">'
                            + '<span id="user-name" class="user-name"></span>'
                            + '</div>'
                            + '</div>'
                            + '<span style="font-size:1.8rem">Đã gửi yêu cầu kết bạn</span>'
                            + '</form>'
                            + '</div>'
                            + '<div class="list-messages-contain">'
                            + '<ul id="chat" class="list-messages">'
                            + '</ul>'
                            + '</div>';

                    document.getElementById("receiver").innerHTML = rightSide;
                }
                // this user không phải là ng gửi kb và chưa accept(được ng kia gửi kb và chưa accept)
                else if (data.status === false && data.owner !== accountIdToCompare && data.owner !== 0) {
                    rightSide = '<div class="user-contact">' + '<div class="back">'
                            + '<i class="fa fa-arrow-left"></i>'
                            + '</div>'
                            + '<div class="user-contain">'
                            + '<div class="user-img">'
                            + '<img src="' + receiverAvatar + '" '
                            + 'alt="Image of user">'
                            + '<div class="user-img-dot ' + status + '"></div>'
                            + '</div>'
                            + '<div class="user-info">'
                            + '<span id="user-name" class="user-name"></span>'
                            + '</div>'
                            + '</div>'         
                            + '</div>'
                            + '<div class="list-messages-contain">'
                            + '<ul id="chat" class="list-messages">'
                            + '</ul>'
                            + '</div>'
                            + '<form action="http://' + window.location.host + '/ManageLodgingHouse/chat" method="post" '
                            + ' style="display:flex; justify-content:center">'
                            + '<input type="hidden" name="sender" value="' + accountId + '">'
                            + '<input type="hidden" name="receiver" value="' + receiver + '">'
                            + '<input type="hidden" name="status" value="true">'
                            + '<input type="hidden" name="isAccept" value="true">'
                            + '<button type="submit" class="btn-donate">Chấp nhận yêu cầu kết bạn</button>'
                            + '</form>';
                    document.getElementById("receiver").innerHTML = rightSide;

                } else if (data.status === false && data.sender === 0 && data.receiver === 0) {
                    rightSide = '<div class="user-contact">' + '<div class="back">'
                            + '<i class="fa fa-arrow-left"></i>'
                            + '</div>'
                            + '<div class="user-contain">'
                            + '<div class="user-img">'
                            + '<img src="' + receiverAvatar + '" '
                            + 'alt="Image of user">'
                            + '<div class="user-img-dot ' + status + '"></div>'
                            + '</div>'
                            + '<div class="user-info">'
                            + '<span id="user-name" class="user-name"></span>'
                            + '</div>'
                            + '</div>'
                            + '</div>'
                            + '<div class="list-messages-contain">'
                            + '<ul id="chat" class="list-messages">'
                            + '</ul>'
                            + '</div>'
                            + '<form action="http://' + window.location.host + '/ManageLodgingHouse/chat" method="post" '
                            + ' style="display:flex; justify-content:center">'
                            + '<input type="hidden" name="sender" value="' + accountId + '">'
                            + '<input type="hidden" name="receiver" value="' + receiver + '">'
                            + '<input type="hidden" name="status" value="false">'
                            + '<input type="hidden" name="isAccept" value="false">'
                            + '<button type="submit" class="btn-donate">Kết Bạn</button>'
                            + '</form>';
                    document.getElementById("receiver").innerHTML = rightSide;
                }

                displayFullName(receiver);
                handleResponsive();
            })
            .catch(ex => {
                console.log(ex);
            });
}

function displayFullName(receiverId) {
    var fullNameEle = document.getElementById('user-name');

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var fullName = this.responseText;
            fullNameEle.textContent = fullName;
        }
    };

    xhttp.open("GET", "http://" + window.location.host + "/ManageLodgingHouse/users-rest-controller?receiverId=" + receiverId, true);
    xhttp.send();
}

function fetchGroup() {
    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller?username=" + username)
            .then(function (data) {
                return data.json();
            })
            .then(data => {

                document.querySelector(".left-side .list-user").innerHTML = "";
                data.forEach(function (data) {
                    let numberMember = data.users ? data.users.length : 0;

                    let findObject = data.users.find(element => element.username == username);
                    let isAdmin = findObject.admin;

                    let imgSrc = ' src="http://' + window.location.host + '/ManageLodgingHouse/files/group-' + data.id + '/' + data.avatar + '"';
                    let appendUser = '<li id="group-' + data.id + '">'
                            + '<div class="user-contain" data-id="' + data.id + '" data-number="' + numberMember + '" data-name="' + data.name + '" onclick="setGroup(this);">'
                            + '<div class="user-img">'
                            + '<img id="img-group-' + data.id + '"'
                            + imgSrc
                            + ' alt="Image of user">'
                            + '</div>'
                            + '<div class="user-info" style="flex-grow:1 ;">'
                            + '<span class="user-name">' + data.name + '</span>'
                            + '</div>'
                            + '</div>';
                    if (isAdmin) {
                        appendUser += '<div class="group-delete border" data-id="' + data.id + '" onclick="deleteGroup(this)">Delete</div>';
                    }
                    appendUser += '</li>';
                    document.querySelector(".left-side .list-user").innerHTML += appendUser;
                });
            }).catch(ex => {
        console.log(ex);
    });
}


function handleResponsive() {
    back = document.querySelector(".back");
    rightSide = document.querySelector(".right-side");
    leftSide = document.querySelector(".left-side");

    if (back) {
        back.addEventListener("click", function () {
            rightSide.classList.remove("active");
            leftSide.classList.add("active");
            listFile = [];
            renderFile();
        });
    }

    rightSide.classList.add("active");
    leftSide.classList.remove("active");

}

function displayFiles() {
    // thẻ input các file
    attachFile = document.getElementById("attach");

    // thẻ input các file ảnh
    imageFile = document.getElementById("image");

    // thẻ ul li hiển thị các ảnh đã chọn
    file = document.querySelector(".list-file");

    // xoá các file đã chọn
    deleteAttach = document.querySelectorAll(".delete-attach");

    // kích hoạt khi chọn 1 file bất kì
    attachFile.addEventListener("change", function (event) {
        let filesInput = event.target.files;

        // add các file đã chọn vào mảng file
        // khi chọn file thì listFile sẽ được thêm các file đã chọn vào
        for (const file of filesInput) {
            listFile.push(file);
        }

        typeFile = "file";
        renderFile("attach");

        this.value = null;
    });

    // kích hoạt khi chọn 1 file ảnh bất kì
    imageFile.addEventListener("change", function (e) {
        let filesImage = e.target.files;

        // add các file ảnh đã chọn vào mảng file
        for (const file of filesImage) {
            listFile.push(file);
        }

        typeFile = "image";

        renderFile("image");

        this.value = null;
    });
}

// xoá file khỏi danh sách file đã chọn
function deleteFile(idx) {
    if (!isNaN(idx))
        listFile.splice(idx, 1);

    renderFile(typeFile);
}

// hiển thị các file đã chọn trong thẻ input
function renderFile(typeFile) {
    let listFileHTML = "";
    let idx = 0;

    if (typeFile === "image") {
        for (const file of listFile) {
            listFileHTML += '<li><img src="' + URL.createObjectURL(file)
                    + '" alt="Image file"><span data-idx="'
                    + (idx) + '" onclick="deleteFile('
                    + idx + ')" class="delete-attach">X</span></li>';
            idx++;
        }
    } else {
        for (const file of listFile) {
            listFileHTML += '<li><div class="file-input">' + file.name
                    + '</div><span data-idx="'
                    + (idx) + '" onclick="deleteFile('
                    + idx + ')" class="delete-attach">X</span></li>';
            idx++;
        }
    }

    // set html cho list file chosen
    if (listFile.length == 0) {
        file.innerHTML = "";
        file.classList.remove("active");
    } else {
        file.innerHTML = listFileHTML;
        file.classList.add("active");
    }

    deleteAttach = document.querySelectorAll(".delete-attach");
}

function sendMessage(e) {
    e.preventDefault();

    var inputText = document.getElementById("message").value;
    // gửi text
    if (inputText != '') {
        sendText();
    }
    // gửi file, image, audio,...
    else {
        sendAttachments();
    }

    return false;
}

// gửi text thuần
function sendText() {
    var messageContent = document.getElementById("message").value;
    var messageType = "text";
    document.getElementById("message").value = '';
    var message = buildMessageToJson(messageContent, messageType);
    setMessage(message);

    // chuyển accountId và receiver thành int
    message.accountId = parseInt(message.accountId);
    message.receiver = parseInt(message.receiver);
    console.log(message);

    // gửi JSON message tới onMessage() ở Server
    websocket.send(JSON.stringify(message));
}

// gửi file(image, pdf, audio,....)
function sendAttachments() {
    var messageType = "attachment";

    // lúc này user đã chọn các file và lưu các file đã chọn vào listFile và sended
    for (file of listFile) {
        // tên file
        var messageContent = file.name.trim();
        // kiểu file
        messageType = file.type;
        var message = buildMessageToJson(messageContent, messageType);

        // chuyển accountId và receiver thành int
        message.accountId = parseInt(message.accountId);
        message.receiver = parseInt(message.receiver);

        websocket.send(JSON.stringify(message));
        websocket.send(file);

        if (messageType.startsWith("audio")) {
            message.message = '<audio controls>'
                    + '<source src="' + URL.createObjectURL(file) + '" type="' + messageType + '">'
                    + '</audio>';
        } else if (messageType.startsWith("video")) {
            message.message = '<video width="400" controls>'
                    + '<source src="' + URL.createObjectURL(file) + '" type="' + messageType + '">'
                    + '</video>';
        } else if (messageType.startsWith("image")) {
            message.message = '<img src="' + URL.createObjectURL(file) + '" alt="">';
        } else {
            message.message = '<a href= "' + URL.createObjectURL(file) + '">' + messageContent + '</a>';
        }

        // hiển thị các file vừa gửi lên box chat
        setMessage(message);
    }

    file = document.querySelector(".list-file");

    // gửi xong thì ẩn đi block hiển thị các file đã chọn
    file.classList.remove("active");
    // xoá đi các file hiển thị trong block
    file.innerHTML = "";
    // xoá đi các file đã chọn trong mảng file
    listFile = [];
}

function buildMessageToJson(message, type) {
    return {
        accountId: accountId,
        message: message,
        type: type,
        receiver: receiver
    };
}

// set lại đoạn chat khi có gửi tn đi hoặc có tin nhắn tới
function setMessage(msg) {
    console.log("online users: " + msg.onlineList);

    // gửi MessageDTO tin nhắn
    if (msg.message !== '[P]open' && msg.message !== '[P]close') {
        var currentChat = document.getElementById('chat').innerHTML;
        var newChatMsg = '';
        if (msg.receiver !== null) {
            newChatMsg = customLoadMessage(msg.accountId, msg.message);
        }

        // khi nhắn xong, sẽ lấy đoạn chat cũ + message mới
        document.getElementById('chat').innerHTML = currentChat
                + newChatMsg;
        setStyleImageFile();
        goLastestMsg();
    }
    // gửi MessageDTO vừa kết nối
    else {
        if (msg.message === '[P]open') {
            msg.onlineList.forEach(accountId => {
                try {
                    setOnline(accountId, true);
                } catch (ex) {
                }
            });
        } else {
            setOnline(msg.username, false);
        }

    }
}

function setOnline(accountId, isOnline) {
    let ele = document.getElementById('status-' + accountId);

    if (isOnline === true) {
        ele.classList.add('online');
    } else {
        ele.classList.remove('online');
    }
}

//function loadMessagesGroup() {
//    var currentChatbox = document.getElementById("chat");
//    var xhttp = new XMLHttpRequest();
//    xhttp.onreadystatechange = function () {
//        if (this.readyState == 4 && this.status == 200) {
//            var messages = JSON.parse(this.responseText);
//            var chatbox = "";
//            messages.forEach(msg => {
//                try {
//                    chatbox += customLoadMessageGroup(msg.username, groupId, msg.message, msg.avatar);
//                } catch (ex) {
//
//                }
//            });
//            currentChatbox.innerHTML = chatbox;
//            goLastestMsg();
//        }
//    };
//    xhttp.open("GET", "http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller?messagesConversationId=" + groupId, true);
//    xhttp.send();
//}

function loadMessages() {
    // id của box chat
    var currentChatbox = document.getElementById("chat");

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Chuyển đổi giá trị chuỗi thành đối tượng JSON
            var messages = JSON.parse(this.responseText);
            var chatbox = "";
            messages.forEach(msg => {
                try {
                    chatbox += customLoadMessage(msg.accountId, msg.message);
                } catch (ex) {

                }
            });
            currentChatbox.innerHTML = chatbox;
            setStyleImageFile();
            goLastestMsg();
        }
    };

    // lấy ra chuỗi JSON được chuyển từ list message từ server sang
    xhttp.open("GET", "http://" + window.location.host + "/ManageLodgingHouse/chat-rest-controller?sender=" + accountId
            + "&receiver=" + receiver, true);
    xhttp.send();
}

// chia các message tương ứng với sender và receiver(phân tin của sender và receiver sang 2 phía tương ứng)
function customLoadMessage(sender, message) {
    var imgSrc = receiverAvatar;
    var msgDisplay = '<li>'
            + '<div class="message';
    if (receiver != sender && accountId != sender) {
        return '';
    } else if (receiver == sender) {
        msgDisplay += '">';
    } else {
        imgSrc = userAvatar;
        msgDisplay += ' right">';
    }

    msgDisplay += '<div class="message-img">'
            + '<img src="' + imgSrc + '" alt="">'
            + ' </div>';
    if (message.includes("<img")) {
        var array = message.split('"');
        let urlImage = "";

        for (var item in array) {
            if (array[item].includes('http://localhost:9999/')) {
                urlImage = array[item];
                break;
            }
        }

        urlImage = "'" + urlImage + "'";
        msgDisplay += '<div class="message-text" onclick="displayImageOnClick(' + urlImage
                + ')">' + message + '</div>';
    } else {
        msgDisplay += '<div class="message-text">' + message + '</div>';
    }
    msgDisplay += '</div>'
            + '</li>';

    return msgDisplay;
}

function displayImageOnClick(urlImage) {
    var modal = document.getElementById('imageModal');
    var modalImage = document.getElementById('modalImage');
    modal.style.display = "block";
    modalImage.src = urlImage;
}

function closeModal() {
    var modal = document.getElementById('imageModal');
    modal.style.display = "none";
}


//function customLoadMessageGroup(sender, groupIdFromServer, message, avatar) {
//    let imgSrc = 'http://' + window.location.host + '/ManageLodgingHouse/files/' + sender + '/' + avatar;
//    var msgDisplay = '<li>'
//            + '<div class="message';
//    if (groupIdFromServer != groupId) {
//        return '';
//    }
//    if (username != sender) {
//        msgDisplay += '">';
//    } else {
//        imgSrc = userAvatar;
//        msgDisplay += ' right">';
//    }
//    return msgDisplay + '<div class="message-img">'
//            + '<img src="' + imgSrc + '" alt="">'
//            + '<div class="sender-name">' + sender + '</div>'
//            + ' </div>'
//            + '<div class="message-text">' + message + '</div>'
//            + '</div>'
//            + '</li>';
//}

// gửi keyword sang để tìm các user
function searchFriendByKeyword(keyword) {
    fetch("http://" + window.location.host + "/ManageLodgingHouse/users-rest-controller?keyword=" + keyword)
            .then(function (data) {
                return data.json();
            })
            .then(data => {
                document.querySelector(".left-side .list-user").innerHTML = "";
                data.forEach(function (data) {
                    if (data.isOnline)
                        status = "online";
                    else
                        status = "";

                    let appendUser = '<li id="' + data.accountID + '" onclick="setReceiver(this);">'
                            + '<div class="user-contain">'
                            + '<div class="user-img">'
                            + '<img id="img-' + data.accountID + '"'
                            + ' src="http://' + window.location.host + '/ManageLodgingHouse/Resource/images/avatar-default.jpg"'
                            + 'alt="Image of user">'
                            + '<div id="status-' + data.accountID + '" class="user-img-dot ' + status + '"></div>'
                            + '</div>'
                            + '<div class="user-info">'
                            + '<span class="user-name">' + data.fullName + '</span>'
                            + '</div>'
                            + '</div>'
                            + '</li>';
                    document.querySelector(".left-side .list-user").innerHTML += appendUser;
                });
            });
}

function searchMemberByKeyword(ele) {
    let keyword = ele.value;
    fetch("http://" + window.location.host + "/ManageLodgingHouse/users-rest-controller?username=" + username + "&keyword=" + keyword + "&conversationId=" + groupId)
            .then(function (data) {
                return data.json();
            })
            .then(data => {

                document.querySelector(".add-member-body .list-user ul").innerHTML = "";
                data.forEach(function (data) {
                    if (data.online)
                        status = "online";
                    else
                        status = "";

                    let check = "";
                    if (listUserAdd.indexOf(data.username) >= 0)
                        check = "checked";

                    let appendUser = '<li>'
                            + '<input id="member-' + data.username + '" type="checkbox" ' + check + ' value="' + data.username + '" onchange="addUserChange(this)">'
                            + '<label for="member-' + data.username + '">'
                            + '<div class="user-contain">'
                            + '<div class="user-img">'
                            + '<img '
                            + ' src="http://' + window.location.host + '/ManageLodgingHouse/files/' + data.username + '/' + data.avatar + '"'
                            + 'alt="Image of user">'
                            + '</div>'
                            + '<div class="user-info">'
                            + '<span class="user-name">' + data.username + '</span>'
                            + '</div>'
                            + '</div>'
                            + '</label>'
                            + '<div class="user-select-dot"></div>'
                            + '</li>';
                    document.querySelector(".add-member-body .list-user ul").innerHTML += appendUser;
                });
            });
}

function searchGroupByKeyword(value) {
    let keyword = value;
    fetch("http://" + window.location.host + "/ManageLodgingHouse/conversations-rest-controller?username=" + username + "&conversationKeyword=" + keyword)
            .then(function (data) {
                return data.json();
            })
            .then(data => {

                document.querySelector(".left-side .list-user").innerHTML = "";
                data.forEach(function (data) {

                    let numberMember = data.users ? data.users.length : 0;
                    // trong users hiện tại chỉ chứa user admin
                    let findObject = data.users.find(element => element.username == username);
                    let isAdmin = false;

                    if (findObject)
                        isAdmin = findObject.admin;

                    let imgSrc = ' src="http://' + window.location.host + '/ManageLodgingHouse/files/group-' + data.id + '/' + data.avatar + '"';

                    let appendUser = '<li id="group-' + data.id + '">'
                            + '<div class="user-contain" data-id="' + data.id + '" data-number="' + numberMember + '" data-name="' + data.name + '" onclick="setGroup(this);">'
                            + '<div class="user-img">'
                            + '<img id="img-group-' + data.id + '"'
                            + imgSrc
                            + ' alt="Image of user">'
                            + '</div>'
                            + '<div class="user-info" style="flex-grow:1 ;">'
                            + '<span class="user-name">' + data.name + '</span>'
                            + '</div>'
                            + '</div>';
                    if (isAdmin)
                        appendUser += '<div class="group-delete border" data-id="' + data.id + '" onclick="deleteGroup(this)">Delete</div>';

                    appendUser += '</li>';
                    document.querySelector(".left-side .list-user").innerHTML += appendUser;
                });
            });
}

function searchUser(ele) {
    if (typeChat == "user") {
        searchFriendByKeyword(ele.value);
    }
    // trường hợp là conversation
    else {
        if (ele.value == "") {
            fetchGroup();
        } else {
            searchGroupByKeyword(ele.value);
        }
    }
}

function goLastestMsg() {
    var msgLiTags = document.querySelectorAll(".message");
    var last = msgLiTags[msgLiTags.length - 1];
    try {
        last.scrollIntoView();
    } catch (ex) {
    }
}
 