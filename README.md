# supermarket pricer kata quickstart app

##### Please follow the steps below in order to launch the application :

1. `git clone https://github.com/HyounesH/supermarket_pricer_kata_yhamdane.git`
2. run the command `cd supermarket_pricer_kata_yhamdane`
3. run the maven command `mvn clean install`
4. Optional : run the command `mvn test` to check if all testes are passed
5. To launch the app run the command `mvn spring-boot:run`

The application is launched locally in default port 8080. <br>
You can view all the available endpoints under `http://localhost:8080/swagger-ui/index.html#/`

##### To launch the app using docker please follow the steps below: 
PS: Make sure the docker is installed in your machine and `docker version` command works fine. <br>

1. build the image using the command `docker build -t supermarket-pricer .`
2. Run the docker container using the command `docker run -p 8081:8080 --name supermarket-pricer-instance supermarket-pricer`

The app is launched locally under default port 8081. <br>
You can view all the available endpoints under `http://localhost:8081/swagger-ui/index.html#/`



