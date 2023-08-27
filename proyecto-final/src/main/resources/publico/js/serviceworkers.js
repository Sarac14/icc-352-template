/**
 * Ejemplo de un script de service workers.
 */
/*const CACHE_VERSION = 1;
const CURRENT_CACHES = {
    font: `font-cache-v${CACHE_VERSION}`,
};*/
// Cache activo.
const CACHE_NAME = 'mi-app-cache-v1';
const urlsToCache = [
    '/crud-simple/listar',
    '/crud-simple/crear',
    '/uiLogic.js',
    '/visualizar.html',
    '/webWorkers.js',
    '/VerFoto.html',
    '/vermapa.html',
    '/login.html',
    '/serviceWorkers.js',
    '/templates/crud-tradicional/listar.html',
    '/templates/crud-tradicional/crearEditarVisualizar.html',
    '/index1.html'

];
const fallback = "/html5/offline.html";

self.addEventListener('install', function(event) {
    console.log('Instalando el Services Worker');
    // Utilizando las promesas para agregar los elementos definidos
    event.waitUntil(
        caches.open(CACHE_NAME) //Utilizando el api Cache definido en la variable.
            .then(function(cache) {
                console.log('Cache abierto');
                return cache.addAll(urlsToCache); //agregando todos los elementos del cache.
            })
    );
});

self.addEventListener('activate', evt => {
    console.log('Activando el services worker -  Limpiando el cache no utilizado');
    evt.waitUntil(
        caches.keys().then(function(keyList) { //Recupera todos los cache activos.
            return Promise.all(keyList.map(function(key) {
                if (CACHE_NAME.indexOf(key) === -1) { //si no es el cache por defecto borramos los elementos.
                    return caches.delete(key); //borramos los elementos guardados.
                }
            }));
        })
    );
});

self.addEventListener('fetch', event => {
    event.respondWith(
        caches.match(event.request).then(response=>{
            console.log(event);
            //si existe retornamos la petición desde el cache, de lo contrario (retorna undefined) dejamos pasar la solicitud al servidor,
            // lo hacemos con la función fetch pasando un objeto de petición.
            return response || fetch(event.request);
        }).catch(function (){ //En caso de tener un problema con la red, se mostrará el caso
            return caches.match(fallback);
        })
    );
});


/*self.addEventListener('fetch', event => {
    console.log('Manejando evento de fetch para', event.request.url);

    event.respondWith(
        caches.open(CURRENT_CACHES.font).then(cache => {
            return cache.match(event.request).then(response => {
                if (response) {
                    console.log('Respuesta encontrada en caché:', response);
                    return response;
                }

                console.log('No se encontró respuesta para %s en caché. Obteniendo de la red...', event.request.url);

                return fetch(event.request.clone()).then(response => {
                    console.log('Respuesta para %s de la red: %O', event.request.url, response);

                    if (
                        response.status < 400 &&
                        response.headers.has('content-type') &&
                        response.headers.get('content-type').match(/^font\//i)
                    ) {
                        console.log('Almacenando en caché la respuesta para', event.request.url);
                        cache.put(event.request, response.clone());
                    } else {
                        console.log('No se almacena en caché la respuesta para', event.request.url);
                    }

                    return response;
                }).catch(error => {
                    console.error('Error en el manejador de fetch:', error);
                    throw error;
                });
            });
        })
    );
});*/
