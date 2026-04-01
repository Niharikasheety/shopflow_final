pipeline {
    agent any

    environment {
        IMG_NAME  = "shopflow"
        CONTAINER = "shopflow-container"
        PORT      = "9090"
    }

    stages {

        stage("Clone") {
            steps {
                echo "Cloning from GitHub..."
                git branch: "main", url: "https://github.com/Niharikasheety/shopflow_final.git"
            }
        }

        stage("Build") {
            steps {
                echo "Building with Maven..."
                sh "mvn clean package -DskipTests"
            }
        }

        stage("Test") {
            steps {
                echo "Running JUnit tests..."
                sh "mvn test"
            }
        }

        stage("Docker Build") {
            steps {
                echo "Building Docker image..."
                sh "docker build -t ${IMG_NAME} ."
            }
        }

        stage("Deploy") {
            steps {
                echo "Deploying with MySQL..."
                sh """
                    docker network create shopflow-net 2>/dev/null || true
                    docker rm -f mysql-db 2>/dev/null || true
                    docker run -d --name mysql-db --network shopflow-net \
                        -e MYSQL_ROOT_PASSWORD=ShopFlow@123 \
                        -e MYSQL_DATABASE=shopflow \
                        mysql:8
                    echo "Waiting 30s for MySQL to be ready..."
                    sleep 30
                    docker rm -f ${CONTAINER} 2>/dev/null || true
                    docker run -d -p ${PORT}:${PORT} \
                        --name ${CONTAINER} \
                        --network shopflow-net \
                        -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/shopflow?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC \
                        -e SPRING_DATASOURCE_USERNAME=root \
                        -e SPRING_DATASOURCE_PASSWORD=ShopFlow@123 \
                        ${IMG_NAME}
                """
            }
        }
    }

    post {
        success { echo "SUCCESS: App running at http://localhost:9090" }
        failure { echo "FAILED: Check console output above" }
    }
}
