
var registerList = [];
function addRegisterToSystem(pname, psector, plevel, plongitud, platitud, pusuario){
    var newRegister = {
        id: Math.floor(Math.random() * 10000),
        name : pname,
        sector : psector,
        level : plevel,
        longitud : plongitud,
        latitud : platitud,
        usuario : pusuario
    };
    console.log(newRegister);
    registerList.push(newRegister);
    localStorageRegisterList(registerList);
}

function getRegisterList(){
    var storedList = localStorage.getItem('localRegisterList');
     if(storedList == null){
         registerList = [];
     }else{
         registerList = JSON.parse(storedList);
     }
    return registerList;
}

function localStorageRegisterList(plist){
    localStorage.setItem('localRegisterList', JSON.stringify(plist));
}

//////////////////////
//document.querySelector('#btnEnviar').addEventListener('click', saveRegister);
//registerTable();
function  saveRegister(){
    var sId = document.getElementById("id").value;

    if (sId) {
        // Si hay un ID, estamos modificando un registro existente
        for (var i = 0; i < registerList.length; i++) {
            if (registerList[i].id == sId) {
                registerList[i].name = document.getElementById("name").value;
                registerList[i].sector = document.getElementById("sector").value;
                registerList[i].level = document.getElementById("level").value;

                localStorageRegisterList(registerList);
                break;
            }
        }
    }else {
        var sName = document.querySelector('#name').value,
            sSector = document.querySelector('#sector').value,
            sNivel = document.querySelector('#level').value,
            sUsuario = document.querySelector('#user').value,
            sLongitud = document.querySelector('#longitud').value,
            sLatitud = document.querySelector('#latitud').value;

        addRegisterToSystem(sName, sSector, sNivel, sLongitud, sLatitud, sUsuario);
    }
    var formElement = document.querySelector("form");
    formElement.reset();

    registerTable();
}


function registerTable(){
    var list = getRegisterList(),
        tbody = document.querySelector('#registerTable tbody');

    tbody.innerHTML = '';

    for (var i = 0; i < list.length; i++){
        var row = tbody.insertRow(i);
        var idCell = row.insertCell(0),
            nameCell = row.insertCell(1),
            sectorCell = row.insertCell(2),
            levelCell = row.insertCell(3),
            usuarioCell = row.insertCell(4),
            latitudCell = row.insertCell(5),
            longitudCell = row.insertCell(6);




        idCell.innerHTML = list[i].id;
        nameCell.innerHTML = list[i].name;
        sectorCell.innerHTML = list[i].sector;
        levelCell.innerHTML = list[i].level;
        usuarioCell.innerHTML = list[i].usuario;
        latitudCell.innerHTML = list[i].latitud;
        longitudCell.innerHTML = list[i].longitud;

        var actionsCell = row.insertCell(7); // Nueva celda para acciones


        var editButton = document.createElement("button");
        editButton.innerHTML = "Modificar";
        editButton.className = "btn btn-primary btn-sm";
        (function(currentId) {
            editButton.addEventListener("click", function() {
                var register = getRegisterById(currentId);

                document.getElementById("name").value = register.name;
                document.getElementById("sector").value = register.sector;
                document.getElementById("level").value = register.level;
                document.getElementById("latitud").value = register.latitud;
                document.getElementById("longitud").value = register.longitud;
                document.getElementById("user").value = register.usuario;
                document.getElementById("id").value = register.id;
            });
        })(list[i].id);

        actionsCell.appendChild(editButton);

        var deleteButton = document.createElement("button");
        deleteButton.innerHTML = "Eliminar";
        deleteButton.className = "btn btn-danger btn-sm";

        (function(id) {
            deleteButton.addEventListener("click", function() {
                deleteRegister(id);
                registerTable();
            });
        })(list[i].id);

        actionsCell.appendChild(deleteButton);



        tbody.appendChild(row);
    }
}

function deleteRegister(registerId) {
    for (var i = 0; i < registerList.length; i++) {
        if (registerList[i].id == registerId) {
            registerList.splice(i, 1);
            localStorageRegisterList(registerList);
            break;
        }
    }
}

function getRegisterById(registerId) {
    for (var i = 0; i < registerList.length; i++) {
        if (registerList[i].id == registerId) {
            return registerList[i];
        }
    }
}

document.addEventListener("DOMContentLoaded", function() {
    fetch('/obtener-usuario')
        .then(response => response.text())
        .then(usuario => {
            document.getElementById("user").value = usuario;
        });
});





