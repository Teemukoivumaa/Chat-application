const admin = require('firebase-admin');
var secrets = require("./secrets.js");
var firebaseConfig = secrets.firebase;
var serviceAccount = secrets.admin;

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

async function getAllUsers() {
    const snapshot = await db.collection('users').get();
    snapshot.forEach((doc) => {
        console.log(doc.id, '=>', doc.data());
    });
}

//getAllUsers();
