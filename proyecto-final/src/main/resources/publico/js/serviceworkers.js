/**
 * Ejemplo de un script de service workers.
 */
const CACHE_VERSION = 1;
const CURRENT_CACHES = {
    font: `font-cache-v${CACHE_VERSION}`,
};
// Cache activo.
const CACHE_NAME = 'mi-app-cache-v1';
const urlsToCache = [
    '/localStorage.html',
    '/uiLogic.js',
    '/visualizar.html',
    '/webWorkers.js',
    '/VerFoto.html',
    '/vermapa.html',
    '/login.html',
    '/serviceWorkers.js',
    '/index1.html'
];
const fallback = "/html5/offline.html";

self.addEventListener('install', function(event) {
    console.log('Instalando el Service Worker');
    event.waitUntil(
        caches.open(CACHE_NAME)
            .then(function(cache) {
                console.log('Cache abierto');
                return cache.addAll(urlsToCache);
            })
    );
});

self.addEventListener('activate', event => {
    console.log('Activando el Service Worker - Limpiando el cache no utilizado');
    const expectedCacheNamesSet = new Set(Object.values(CURRENT_CACHES));
    event.waitUntil(
        caches.keys().then(cacheNames => Promise.all(
            cacheNames.map(cacheName => {
                if (!expectedCacheNamesSet.has(cacheName)) {
                    console.log('Eliminando caché obsoleta:', cacheName);
                    return caches.delete(cacheName);
                }
            })
        ))
    );
});

self.addEventListener('fetch', event => {
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
});
