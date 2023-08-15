
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



        tbody.appendChild(row);
    }
}

