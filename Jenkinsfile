pipeline {
    agent any

    tools {
        maven 'Maven'   // Name configured in Jenkins Global Tool Configuration
        jdk 'JDK25'     // Name configured in Jenkins Global Tool Configuration
    }

    environment {
        TOMCAT_URL = 'http://localhost:8080'
        TOMCAT_CREDS = credentials('tomcat-deployer')  // Jenkins credentials ID
        WAR_NAME = 'todo-list'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building the project...'
                bat 'mvn clean compile -B'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running unit tests...'
                bat 'mvn test -B'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo '📦 Packaging WAR file...'
                bat 'mvn package -DskipTests -B'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                echo '🚀 Deploying to Tomcat server...'
                deploy adapters: [
                    tomcat9(
                        credentialsId: 'tomcat-deployer',
                        path: '',
                        url: "${TOMCAT_URL}"
                    )
                ],
                contextPath: "/${WAR_NAME}",
                war: "target/${WAR_NAME}.war"
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
            echo "🌐 App available at: ${TOMCAT_URL}/${WAR_NAME}/"
        }
        failure {
            echo '❌ Pipeline failed! Check logs for details.'
        }
        always {
            echo '🧹 Cleaning up workspace...'
            cleanWs()
        }
    }
}
