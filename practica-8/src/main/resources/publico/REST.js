const baseUrl = 'http://localhost:7000/api/estudiante/';

document.getElementById('getAll').addEventListener('click', function() {
    fetch(baseUrl)
        .then(response => response.json())
        .then(data => {
            document.getElementById('allStudents').textContent = JSON.stringify(data);
        });
});

document.getElementById('getOne').addEventListener('click', function() {
    const matricula = document.getElementById('getOneInput').value;
    fetch(baseUrl + matricula)
        .then(response => response.json())
        .then(data => {
            document.getElementById('oneStudent').textContent = JSON.stringify(data);
        });
});

document.getElementById('create').addEventListener('click', function() {
    const nombre = document.getElementById('nombre').value;
    const matricula = document.getElementById('matricula').value;
    const carrera = document.getElementById('carrera').value;
    fetch(baseUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nombre, matricula, carrera }),
    })
        .then(response => response.json())
        .then(data => {
            alert('Estudiante creado con éxito: ' + JSON.stringify(data));
        });
});

document.getElementById('delete').addEventListener('click', function() {
    const matricula = document.getElementById('deleteInput').value;
    fetch(baseUrl + matricula, {
        method: 'DELETE',
    })
        .then(() => {
            alert('Estudiante borrado con éxito');
        });
});
