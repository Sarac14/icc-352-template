'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

<!--AQUIIIIIIIIIIIIIIIIIII-->

/*document.addEventListener("DOMContentLoaded", function() {
    var usernamePage = document.querySelector("#username-page");
    var chatPage = document.querySelector("#chat-page");
    var usernameForm = document.querySelector("#usernameForm");
    var messageForm = document.querySelector("#messageForm");
    var messageInput = document.querySelector("#message");
    var messageArea = document.querySelector("#messageArea");

    var stompClient = null;
    var username = null;

    function connect() {
        var socket = new SockJS("/mensajeServidor");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            console.log("Connected: " + frame);
            showChatPage();
            stompClient.subscribe("/topic/messages", function(message) {
                showMessage(JSON.parse(message.body));
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    function sendMessage() {
        var messageContent = messageInput.value.trim();
        if (messageContent) {
            var message = {
                sender: username,
                content: messageContent
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
            messageInput.value = "";
        }
    }

    function showMessage(message) {
        var messageElement = document.createElement("li");
        messageElement.classList.add("chat-message");
        messageElement.innerHTML = "<strong>" + message.sender + ":</strong> " + message.content;
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }

    function showChatPage() {
        usernamePage.classList.add("hidden");
        chatPage.classList.remove("hidden");
        messageInput.focus();
    }

    usernameForm.addEventListener("submit", function(event) {
        event.preventDefault();
        username = document.querySelector("#name").value.trim();
        if (username) {
            connect();
        }
    });

    messageForm.addEventListener("submit", function(event) {
        event.preventDefault();
        sendMessage();
    });
});*/


usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)