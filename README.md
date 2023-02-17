# Ktor development server
Server for testing projects against backend locally. Not applicable for public network without modification.

### Features
Includes HTTP endpoint with basic and token authentication and socket endpoint

### Run the app
Running application related configs are in the resource/application.conf. By-default Ktor is started at port 8080 and 8443.
The root path (*:8080 e.g. localhost:8080) shows documentation and available paths.

Note: For https locally you need to create the keys before running this or disable https from configuration.

Run locally from root folder:
```
./gradlew run
```

Run from docker container:
Currently not working because ktor doesn't find configuration file ... 

### Https for local development
Create certificate for https connection
```
make create-keys
```
or
```
keytool -keystore keystore/ssl-engine-main/keystore.jks -alias developmentAlias -genkeypair -keyalg RSA -keysize 4096 -validity 3 -dname 'CN=localhost, OU=developmentKtor, O=developmentKtor, L=Unspecified, ST=Unspecified, C=FI' -ext 'SAN:c=DNS:localhost,IP:127.0.0.1'
```
Default password should be development

Ktor's configuration to this is in resources/application.conf -> ktor->security->ssl
