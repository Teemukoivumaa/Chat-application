
// -- RENAME TO SECRETS.JS IF POPULATED WITH REAL INFORMATION -- //

var firebaseConfig = {
    apiKey: "API_KEY",
    authDomain: "PROJECT_ID.firebaseapp.com",
    databaseURL: "https://PROJECT_ID.firebaseio.com",
    projectId: "PROJECT_ID",
    storageBucket: "PROJECT_ID.appspot.com",
    messagingSenderId: "SENDER_ID",
    appId: "APP_ID",
    measurementId: "G-MEASUREMENT_ID",
  };

  var adminAccount = {
    "type": "service_account",
    "project_id": "PROJECT_ID",
    "private_key_id": "KEY_ID",
    "private_key": "PRIVATE_KEY",
    "client_email": "ADMIN_EMAIL",
    "client_id": "CLIENT_ID",
    "auth_uri": "AUTH_URL",
    "token_uri": "TOKEN_URI",
    "auth_provider_x509_cert_url": "AUTH_PROVIDER",
    "client_x509_cert_url": "CERT_URL"
};
module.exports = {firebaseConfig, adminAccount}