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
   
	Start using command: docker-compose up --build
	Stop using command: docker-compose down -v    
	
    
