let socket = new WebSocket("wss://proyecto.sarablog.me/sync");


socket.onopen = function(e) {
    console.log("Conexión establecida");
    // Aquí puedes enviar los datos iniciales o cualquier otra acción al conectar
};

socket.onmessage = function(event) {
    console.log(`Datos recibidos del servidor: ${event.data}`);
    // Procesa la respuesta aquí
};

socket.onclose = function(event) {
    if (event.wasClean) {
        console.log(`Conexión cerrada limpiamente`);
    } else {
        console.log('Conexión cerrada');
    }
    console.log('Código: ' + event.code + ' razón: ' + event.reason);
};

socket.onerror = function(error) {
    console.log(`Error: ${error.message}`);
};

function sendDataToServer(data) {
    socket.send(data);
}


self.onmessage = function(e) {
    sendDataToServer(JSON.stringify(e.data));
}

