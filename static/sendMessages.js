// browserify sendMessages.js -o bundle.js

async function getPage(url) { // get results
    const axios = require('axios');

    return axios({
            url: url,
            method: 'get',
            headers: {
                'Content-Type': 'application/json',
            }
        })
       .then(res => res.data) // return results
       .catch (err => console.error(err))
}


async function sendMessage() { // send message
    var message = document.getElementById('message').value;
    if (message) { // if message empty dont send
        var url = 'http://78.27.85.73:8080/sendToPhone/' + message
        await getPage(url) // send message
        
        var container = document.getElementById('chat'); // set message to screen
        var newMessage = "<div class='col' id='rcorners2'>"+message+"</div></br>"
        container.innerHTML += newMessage
        document.getElementById('message').value = ""
        chat.scrollTop = chat.scrollHeight
    }
}

async function retriveMessage() { // get messages
    var url = 'http://78.27.85.73:8080/retriveMessageForComputer'
    var response = await getPage(url) // get message

    if (response) { // if empty don't put as a message
        var container = document.getElementById('chat');
        const responseArray = Array.from(response);
        for (let i = 0; i < responseArray.length; i++) {
            var msg = responseArray[i]
            var newMessage = "<div class='col' id='rcorners1'>"+msg+"</div> </br>";
            container.innerHTML += newMessage
        }
        chat.scrollTop = chat.scrollHeight
    }
}

document.getElementById("message").addEventListener("keyup", (event) => { // listen for enter down
  if (event.key === "Enter") {
    sendMessage()
  }
});

document.getElementById("btnSend").addEventListener("click", function() { // listen for button click
    sendMessage()
});


const chat = document.getElementById("chat")

setInterval(function() { // refresh every sec
    retriveMessage();
}, 1000);