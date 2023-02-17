application := development-server

docker-build:
	docker build -t $(application) .

docker-run:
	docker run -p 8080:8080 -p 8443:8443 $(application)

docker-shell:
	docker run -it --entrypoint /bin/bash $(application)