<a name="">![Java 8](https://img.shields.io/badge/Java-8-cd853f "Java 8")</a>
<a name="">![Spring Boot 2.7.15](https://img.shields.io/badge/Spring%20Boot-2.7.15-6db33f "Spring Boot 2.7.15")</a>
<a name="">![Maven](https://img.shields.io/badge/Built%20with-Maven-f76504 "Maven")</a>

# 📚 Spring Boot 2 &ndash; Book Catalog Service

This project provides a **REST API** for managing a book catalog and a web-based interface for interacting with it. It consists of:  

1. **Book Catalogue Management Rest Service**  
2. **Book Catalogue Web Service (JSP UI)**  
3. **Book Catalogue Web Service (Thymeleaf UI)**  

The Thymeleaf implementation is provided to overcome JSP limitations when packaged as an executable JAR.

### JSP Limitations

> When running a Spring Boot application that uses an embedded servlet container (and is packaged as an executable archive), there are some limitations in the JSP support. 
With Jetty and Tomcat, it should work if you use war packaging. An executable war will work when launched with java -jar, and will also be deployable to any standard container. 
JSPs are not supported when using an executable jar.

For more details, you can read the spring docs [here](https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.embedded-container.jsp-limitations:~:text=that%20option%20carefully.-,JSP%20Limitations,for%20error%20handling.%20Custom%20error%20pages%20should%20be%20used%20instead.,-Web).

---

## 📁 Project Structure

### 📌 Book Catalogue Management Rest Service

This module includes:
- **API Endpoints** for book catalog management.
- **Swagger UI** for testing and exploring APIs interactively.
- **Actuator Health Check** at `/actuator/health` for service monitoring.
- **Unit Tests**.

Runs on **port `8080`**. Ensure no other services occupy this port.  

---

### 📌 Book Catalogue Web Service JSP (UI)

This UI interacts with the REST API backend to manage books. Implemented using **Java Server Pages (JSP)**.  

Runs on **port `8081`**.  
🚨 **Note**: JSP has limitations when used with an executable JAR. Use an IDE for testing.  

---

### 📌 Book Catalogue Web Service Thymeleaf (UI)

A modern UI using **Thymeleaf**, serving the same purpose as the JSP UI but with better compatibility for JAR packaging.  

Runs on **port `9000`**.  

---

## ⚙️ How to Build and Run the Project

### Prerequisites

Ensure the following are installed:
- **Java 8**
- **Maven**
- **Git**

Clone the repository:  
```bash
git clone https://github.com/zedbeit/hire-assessment.git
```

Start by running the REST service before starting any UI services.
If using IDE, open each service in a separate IDE window.

### 🚀 Book Catalogue Management Rest Service

#### Using IntelliJ IDEA
1. Import the project: `File -> Open`.
2. Set SDK to **JDK 1.8**: `File -> Project Structure -> SDK`.
3. Run Maven `Install` from the **Maven Tool Window** (skip tests if desired).
4. Locate the `Main` class and click **Run**.

#### Using Maven (Command Line)
```bash
mvn clean package
cd target/
java -jar book-management-rest-service.jar
```
#### Using Maven Spring Boot Goal (Command Line)
```
mvn spring-boot:run
```

Once running:

* Swagger UI: http://localhost:8080/swagger-ui
* Actuator Health: http://localhost:8080/actuator/health


### 🌐 Book Catalogue Web Service JSP (UI)

#### Using IntelliJ IDEA

Follow the steps outlined for the REST service.

Runs on port 8081. Access it via: http://localhost:8081


### 🌐 Book Catalogue Web Service Thymeleaf (UI)

#### Using IntelliJ IDEA or Command Line
Follow the same steps as above.

Runs on port 9000. Access it via: http://localhost:9000

### ℹ️ Issues or Contributions
If you encounter any issues or have suggestions:

* Open an issue in the repository.
* PRs for improvements or workarounds (e.g., JSP limitations) are welcome! 😊

