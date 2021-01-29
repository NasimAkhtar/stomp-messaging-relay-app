var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        sendName();
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(
                JSON.parse(greeting.body).name,
                JSON.parse(greeting.body).content)
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log('sent name...!!!')
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function sendMessage() {
    console.log('sent message...!!!')
    stompClient.send("/app/message",
        {},
        JSON.stringify(
            {
                'name': $("#name").val(),
                'message': $("#message").val()
             }
         )
    );
}

function showGreeting(name, message) {
    $("#greetings")
        .append(
            "<tr><td>"
            + name
            + " : "
            + message
            +  "</td></tr>");
}

function loadHistory() {
    fetch('http://localhost:8080/greetings')
        .then(response => {
            return response.json();
        }).then(data => {
            console.log(data);
            $("#greetings").removeData();
            for (let i = 0; i < data.length; i++) {
                    showGreeting(data[i].name, data[i].content);
                 }
            }
        );
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
    $( "#history" ).click(function() { loadHistory(); });
});

