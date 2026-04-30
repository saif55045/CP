# ✅ Todo List App

A simple Java web-based Todo List application with CI/CD pipeline.

## 🛠️ Tech Stack

- **Backend**: Java Servlets (JSP)
- **Build Tool**: Maven
- **Testing**: JUnit 5
- **Server**: Apache Tomcat 9
- **CI/CD**: Jenkins (Declarative Pipeline)

## 📋 Features

- ✅ Add, toggle, and delete todos
- 🎯 Priority levels (Low, Medium, High)
- 🔍 Filter by status (All, Pending, Completed)
- 🗑️ Clear completed todos
- 📊 Real-time stats dashboard
- 🎨 Modern glassmorphism UI

## 🚀 Local Setup

### Prerequisites
- Java 11+
- Maven 3.6+
- Apache Tomcat 9

### Build & Run
```bash
# Build the project
mvn clean package

# Copy WAR to Tomcat
cp target/todo-list.war $CATALINA_HOME/webapps/

# Start Tomcat
$CATALINA_HOME/bin/startup.sh

# Access at: http://localhost:8080/todo-list/
```

### Run Tests
```bash
mvn test
```

## 🔄 CI/CD Pipeline

The `Jenkinsfile` defines a full CI/CD pipeline:

1. **Checkout** – Pull from GitHub
2. **Build** – `mvn clean compile`
3. **Test** – `mvn test` with JUnit reports
4. **Package** – `mvn package` (WAR file)
5. **Deploy** – Auto-deploy to Tomcat server

### Jenkins Setup
1. Install plugins: **Deploy to Container**, **Pipeline**, **Git**
2. Configure tools: **Maven** (`Maven`), **JDK** (`JDK11`)
3. Add credentials: Tomcat manager (`tomcat-deployer`)
4. Create Pipeline job pointing to this repo's `Jenkinsfile`

## 📁 Project Structure
```
CP/
├── pom.xml
├── Jenkinsfile
├── src/
│   ├── main/
│   │   ├── java/com/todoapp/
│   │   │   ├── model/Todo.java
│   │   │   ├── service/TodoService.java
│   │   │   └── servlet/TodoServlet.java
│   │   └── webapp/
│   │       ├── index.jsp
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── views/todo.jsp
│   └── test/java/com/todoapp/
│       ├── model/TodoTest.java
│       └── service/TodoServiceTest.java
└── requirements.txt
```

## 👤 Author
Saif - BSSE 8th Semester - DevOps Course Project
