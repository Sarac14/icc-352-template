
var registerList = [];
function addRegisterToSystem(pname, psector, plevel, plongitud, platitud){//, pusuario){
    var newRegister = {
        id: Math.floor(Math.random() * 10000),
        name : pname,
        sector : psector,
        level : plevel,
        longitud : plongitud,
        latitud : platitud,
        //usuario : pusuario
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
    var sName = document.querySelector('#name').value,
        sSector = document.querySelector('#sector').value,
        sNivel = document.querySelector('#level').value,
        // sUsuario = document.querySelector('#usuario').value,
        sLongitud = document.querySelector('#longitud').value,
        sLatitud = document.querySelector('#latitud').value;

    addRegisterToSystem(sName, sSector, sNivel, sLongitud, sLatitud);//, sUsuario);
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
            //  usuarioCell = row.insertCell(4),
            latitudCell = row.insertCell(4),
            longitudCell = row.insertCell(5);




        idCell.innerHTML = list[i].id;
        nameCell.innerHTML = list[i].name;
        sectorCell.innerHTML = list[i].sector;
        levelCell.innerHTML = list[i].level;
        // usuarioCell.innerHTML = list[i].usuario;
        latitudCell.innerHTML = list[i].latitud;
        longitudCell.innerHTML = list[i].longitud;

        var actionsCell = row.insertCell(6); // Nueva celda para acciones


        var editButton = document.createElement("button");
        editButton.innerHTML = "Modificar";
        editButton.className = "btn btn-primary btn-sm";
        editButton.addEventListener("click", function() {
        });
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



