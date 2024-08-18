document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const userData = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    fetch("/login/complete", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                // Send user data to WebSocket endpoint
                sendUserToChat(data.userName);

                // Redirect to the chat page
                window.location.href = '/topic/public';
            });
        }
    });
});


function sendUserToChat(username) {
    // Create a WebSocket connection
    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        // Send the username to the WebSocket endpoint
        stompClient.send("/app/chat.addUser", {}, JSON.stringify({
            sender: username,
            type: 'JOIN'
        }));
    }, function(error) {
        console.error('Error connecting to WebSocket: ', error);
    });
}
