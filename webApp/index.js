var StaticServer = require('static-server');
var server = new StaticServer({
    rootPath: './static',            // required, the root of the server file tree
    port: 8080,               // required, the port to listen
    name: 'my-http-server',   // optional, will set "X-Powered-by" HTTP header
    host: '0.0.0.0',       // optional, defaults to any interface
    cors: '*',                // optional, defaults to undefined
    followSymlink: true,      // optional, defaults to a 404 error
});

server.start(function () {
    console.log('Server listening to', server.port);
});