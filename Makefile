application := development-server

docker-build:
	docker build -t $(application) .

docker-run:
	docker run -p 8080:8080 -p 8443:8443 $(application)

docker-shell:
	docker run -it --entrypoint /bin/bash $(application)

docker-build-run: docker-build docker-run

jar-build:
	./gradlew buildFatJar

jar-run:
	java -jar build/libs/development-server.jar

jar-build-run: jar-build jar-run

create-keys:
	keytool -keystore keystore/ssl-engine-main/keystore.jks -alias developmentAlias -genkeypair -keyalg RSA -keysize 4096 -validity 3 -dname 'CN=localhost, OU=developmentKtor, O=developmentKtor, L=Unspecified, ST=Unspecified, C=FI' -ext 'SAN:c=DNS:localhost,IP:127.0.0.1'