const cool = require('cool-ascii-faces')
const express = require('express')
const multer = require('multer')
const path = require('path')
const PORT = process.env.PORT || 5000
const { Pool } = require('pg');
const pool = new Pool({
  connectionString: process.env.DATABASE_URL,
  ssl: true
});
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, './public/images/')
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname)
  }
});
const upload = multer({storage: storage});

var app = express();
var fs = require('fs');
var bodyParser = require("body-parser");

var serveIndex = require('serve-index');
app.use(express.static(__dirname + "/"))
app.use('/public', serveIndex(__dirname + '/public'));

app.use(bodyParser.json({limit:'25mb'}));
app.use(bodyParser.urlencoded({limit:'25mb', extended: true})); //Soporte para decodificar las url
app.use(express.static(path.join(__dirname, 'public')))

app.set('views', path.join(__dirname, 'views'))
app.set('view engine', 'ejs')

var fs = require('fs');
var dir = './public/images';

if (!fs.existsSync(dir)){
    fs.mkdirSync(dir);
}
  
app.get('/', (req, res) => res.render('pages/index'))

app.get('/getAllDrivers', async (req, res) => {
    try {
      const client = await pool.connect();
      const result = await client.query('SELECT * FROM conductor');
      const results = { 'results': (result) ? result.rows : null};

      res.setHeader("Content-Type", "application/json");
      res.send(JSON.stringify(result.rows));
	  res.render('pages/db', results );
      client.release();
    } catch (err) {
      console.error(err);
      res.send("Error " + err);
    }
})

 /*
obtiene buffer de imagen
fs.readFile(path.resolve(__dirname + '/images') + '/cameraman.png', async function read(err, data) {
			res.writeHead(200, {'Content-type':'image/png'});
			res.end(data, 'Base64');
				
		}); */

/*Obtiene nombre de imagenes de carpeta*/
function getImages(callback) {
	var imageDir = path.resolve(__dirname + '/images');
    var fileType = '.png',
        files = [], i;
    fs.readdir(imageDir, function (err, list) {
        for(i=0; i<list.length; i++) {
			files.push(list[i]); //store the file name into the array files
        }
        callback(err, files);
    });
}

app.post('/deleteImage', async (req, res) => {
	var nameFile = req.body.nameFile;
	var pathFile = path.resolve(__dirname + '/public/images') + '/' + nameFile;

	fs.unlinkSync(pathFile);
	res.json({fileDeleted: pathFile});
})

app.post('/saveImage', upload.single('image'), async (req, res) => {
    if(req.file) {
        res.json({name: req.file.originalname});
    }
    else res.send("Error " + err);
});
   

app.post('/saveDriver', async (req, res) => {
	try {
	    var driverName = req.body.nombre;
	    const client = await pool.connect();
	    const result = await client.query('INSERT INTO conductor(nombre, apellido, dni) values ($1, $2, $3) RETURNING dni',
	    	[driverName, '', 11111111]);

	    res.setHeader("Content-Type", "application/json");
	    res.send(JSON.stringify(result));
	    client.release();
    } catch (err) {
      console.error(err);
      res.send("Error " + err);
    }
  })
  
app.listen(PORT, () => console.log(`Listening on ${ PORT }`))
