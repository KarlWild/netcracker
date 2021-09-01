let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;
let to;

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}

function render(message, userName) {
    scrollToBottom();
    // responses
    var templateResponse = Handlebars.compile($("#message-response-template").html());
    var contextResponse = {
        response: message,
        time: getCurrentTime(),
        userName: userName
    };

    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 1);
}

function renderAllMessages(message) {
    scrollToBottom();
    let templateResponse;
    let contextResponse;
    // responses
    if (message.senderName === user) {

        templateResponse = Handlebars.compile($("#message-template").html());
        contextResponse = {
            messageOutput: message.content,
            time: message.timestamp,//.toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3"),
            toUserName: message.senderName
        };
    } else {
         templateResponse = Handlebars.compile($("#message-response-template").html());
         contextResponse = {
            response: message.content,
            time: message.timestamp,//.toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3"),
            userName: message.senderName
        };
    }
    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 1);
}

// function sendMessage(message) {
//     let id;
//     let preview = document.getElementById("selectedUserId");
//     //if(selectedUserId!=null) id = selectedUserId;
//     id = preview.getAttribute("chatIdAttr");
//     $.get(url + "/chat/" + id, function (response) {
//         to = response;
//         console.log(to);
//         sendMsg(message, to);
//         scrollToBottom();
//         if (message.trim() !== '') {
//             var template = Handlebars.compile($("#message-template").html());
//             var context = {
//                 messageOutput: message,
//                 time: getCurrentTime().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3"),
//                 toUserName: selectedUser
//             };
//
//             $chatHistoryList.append(template(context));
//             scrollToBottom();
//             $textarea.val('');
//         }
//     });
// }

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function getCurrentTime() {
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();