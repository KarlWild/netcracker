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
                if (selectedUserName === data.senderName) {
                    render(data.message, data.senderName);
                } else {
                    newMessages.set(data.senderName, data.message);
                    $('#userNameAppender_' + data.senderName).append('<span id="newMessage_' + data.senderName + '" style="color: red">+1</span>');
                }
            }
        );
    });
    $.get(url + "/chat/messages?authedUser="+userId+"&toUser="+selectedUserId, function (response){
        let messages = response;
        for(let i = 0; i<messages.length;i++){
            renderAllMessages(messages[i]);
        }
    });
}

connectToChat();

function sendMsg(text, to) {
    var chatMessage = {
        senderId: userId,
        recipientId: selectedUserId,
        senderName: user,
        recipientName: selectedUserName,
        content: text,
        timestamp: new Date(),
    };
    stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));

}
