# Maersk Container Booking - Full-Stack Application

This project is a complete full-stack application for booking shipping containers with Maersk. It includes a reactive Spring Boot backend, a MongoDB
 database, and a React frontend, all containerized with Docker for easy setup and deployment.

## Features

-   **Backend**: Reactive REST API built with **Spring Boot 3** and **Java 17**.
-   **Frontend**: Modern UI built with **React**.
-   **Database**: **MongoDB** for data persistence.
-   **Containerized**: Fully containerized using **Docker** and **Docker Compose** for one-command setup.
-   **API Documentation**: Integrated **Swagger UI** for interactive API testing.

## Project Structure

 ├── maersk-booking-api/   # Spring Boot Backend Microservice
 │   └── Dockerfile
 ├── maersk-booking-ui/    # React Frontend Application
 │   └── Dockerfile
 ├── docker-compose.yml    # Main Docker Compose orchestration file
 └── README.md             # This file
---

## 🚀 Getting Started: The One-Command Setup

With this setup, you can get the entire application (database, backend, and frontend) running with a single command.

### Prerequisites

1.  **Git**: You need Git to clone the repository.
2.  **Docker & Docker Compose**: You must have Docker Desktop (or Docker Engine with the Compose plugin) installed and running on your system. You can download it from the [official Docker website](https://www.docker.com/products/docker-desktop/).

### Installation & Launch

1.  **Clone the Repository**
    Open your terminal or command prompt and clone the project to your local machine.
	git clone <your-repository-url>
    	cd <repository-folder-name>
    
2. **Run with Docker**
    Run with Docker Compose From the root directory of the project (where the docker-compose.yml file is located), run the following command:
	Start: docker-compose up --build
	Stop: docker-compose down -v    

## Open Application ##

1. open url http://localhost:3000/ - which will open the UI to interact with Maersk booking system

2. Backend API/s is running on 9090

3. Swagger - you can open swagger using http://localhost:9090/swagger-ui.html



## MONGO DB UI ##

Step 1: Download and Install MongoDB Compass 
	If you don't have it, download it from the official MongoDB website.

Step 2: Create a New Connection

	1.Open MongoDB Compass. You will be greeted with a connection screen.

	2.Click on "Fill in connection fields individually".

	3.Enter the following details:
		•Hostname: localhost
		•Port: 27017
		•Leave the Authentication dropdown as "None". The Docker setup doesn't have a username or password.

	It should look like this:

Step 3: Connect and Find Your Data

	1.Click the "Connect" button.
	
	2.On the left-hand side, you will see a list of databases. Find and click on the maersk_bookings database.

	3.Inside the database, you will see your bookings collection. Click on it.

## MVN TEST ##
	
Note: Before running below commands for test cases (make sure spring boot application is running -  which is this docker-compose up --build)

	mvn clean install 

	ex: [maersk-booking-api> mvn clean install]

## Check test coverage report ##

	Go to maersk-booking-api\target\site\jacoco and then open index.html


