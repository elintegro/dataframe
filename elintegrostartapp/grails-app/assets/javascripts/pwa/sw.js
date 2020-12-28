const cacheName = "elintegro-cache";
const assets = [
    '/',
    '/assets/erf/erfVueController.js',
    '/assets/vuejs/vuetify-v2.0.5.css',
    '/assets/erf/gc-vue.css',
    '/assets/erf/homePageLayout.css',
    'https://fonts.googleapis.com/css?family=Lato:300,400,700'
];

// install event
self.addEventListener('install', function(event){
    console.log("Service Worker install ..")
    event.waitUntil(
        caches.open(cacheName).then(function(cache){
            console.log("Caching contents")
            return cache.addAll(assets);
        })
    )
});

// activate event
self.addEventListener('activate', evt => {
    console.log("Service Worker activated ..")
    evt.waitUntil(
        caches.keys().then(keys => {
            return Promise.all(keys
                .filter(key => key !== cacheName)
                .map(key => caches.delete(key))
            );
        })
    );
});

//fetch event
self.addEventListener('fetch', function(event){
    console.log("Fetched resource "+ event.request.url);
    event.respondWith(
        caches.match(event.request).then(function(response){
            return response || fetch(event.request).then((response) => {
                return caches.open(cacheName).then((cache) => {
                    console.log('[Service Worker] Caching new resource: '+event.request.url);
                    cache.put(event.request, response.clone());
                    return response;
                });
            })
        })
    )
});
