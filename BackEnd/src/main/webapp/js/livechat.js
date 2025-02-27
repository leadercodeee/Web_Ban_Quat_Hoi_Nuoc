function sendMessage() {
    const input = document.getElementById('chatInput');
    const message = input.value.trim();
    
    if (message) {
        addMessage('user', message);
        input.value = '';

        setTimeout(() => {
            addMessage('bot', 'Cảm ơn bạn đã liên hệ. Chúng tôi sẽ trả lời sớm nhất có thể.');
        }, 1000);
    }
}

function addMessage(sender, text) {
    const chatMessages = document.getElementById('chatMessages');
    
    const messageElement = document.createElement('div');
    messageElement.classList.add('message', sender);
    messageElement.innerText = text;

    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}
