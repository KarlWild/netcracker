const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let newMessages = new Map();

function connectToChat() {
    var socket = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected");
        stompClient.subscribe(
            "/user/" + userId + "/queue/messages", function (response){
                let data = JSON.parse(response.body);
                console.log("data is : "+data);
                if (selectedUser.email === data.senderName) {
                    render(data.message, data.senderName);
                 } else {
                     newMessages.set(data.senderName, data.message);
                     $('#userNameAppender_' + data.senderName).append('<span id="newMessage_' + data.senderName + '" style="color: red">+1</span>');
                 }
            }
        );
    });
}

connectToChat();

function sendMessage(message) {
    let id;
    let preview = document.getElementById("selectedUserId");
    //if(selectedUserId!=null) id = selectedUserId;
    id = preview.getAttribute("chatIdAttr");
    $.get(url + "/chat/" + id, function (response) {
        to = response;
        console.log(to);
        sendMsg(message, to);
        scrollToBottom();
        if (message.trim() !== '') {
            var template = Handlebars.compile($("#message-template").html());
            var context = {
                messageOutput: message,
                time: getCurrentTime().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3"),
                toUserName: selectedUser
            };

            $chatHistoryList.append(template(context));
            scrollToBottom();
            $textarea.val('');
        }
    });
}
function sendMsg(text, to) {
    var chatMessage = {
        senderId: userId,
        recipientId: selectedUser.userId,
        senderName: user,
        recipientName: selectedUser.email,
        content: text,
        timestamp: new Date(),
    };
    stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));

}

function selectUser(id) {
    removeLiElements();
    $.get(url + "/chat/" + id, function (response) {
        selectedUser = response;
        $('#selectedUserId').html('');
        let preview = document.getElementById("selectedUserId");
        preview.setAttribute("chatIdAttr", selectedUser.userId);
        $('#selectedUserId').append('Чат с ' + selectedUser.last_name + " " + selectedUser.first_name);
        $.get(url + "/chat/messages?authedUser="+userId+"&toUser="+selectedUser.userId, function (response){
           let messages = response;
           for(let i = 0; i<messages.length;i++){
               renderAllMessages(messages[i]);
            }
        });
    });
}
function removeLiElements(){
    let ul = document.getElementById("chatHistoryId");
    let li = ul.parentNode.getElementsByTagName("li")
    let ulist = ul.getElementsByTagName("ul");
    let liItems = ulist[0];
    for(let i = 0;i<li.length; i++){
        liItems.removeChild(li[i]);
    }
}