const functions = require('firebase-functions');
const admin = require('firebase-admin');
var requestify = require('requestify'); 
admin.initializeApp(functions.config().firebase);
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions


function updateKP(name, index)
{
	 admin.database().ref('/KP').child(name).set(index);
}


function updateCloud(name, index)
{
	 admin.database().ref('/Cloud').child(name).set(index);
}

exports.uploadData = functions.https.onRequest((req, res) => {

 updateKP('today', 18);

 res.send("updated");

});






exports.helloWorld = functions.https.onRequest((request, res) => {
 var url = "http://services.swpc.noaa.gov/text/27-day-outlook.txt";
	 requestify.get(url).then(function(response) {
			// Get the response body
			response.getBody();
			res.send(response.body);
	});

});



// // Take the text parameter passed to this HTTP endpoint and insert it into the
// // Realtime Database under the path /messages/:pushId/original
// exports.addMessage = functions.https.onRequest((req, res) => {
//   // Grab the text parameter.
//   const original = req.query.text;
//   // Push the new message into the Realtime Database using the Firebase Admin SDK.
//   admin.database().ref('/messages').push({original: original}).then(snapshot => {
//     // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
//     res.redirect(303, snapshot.ref);
//   });
// });


