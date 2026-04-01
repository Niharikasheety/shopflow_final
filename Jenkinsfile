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

                sh '''
                    echo "Creating Docker network..."
                    docker network create shopflow-net 2>/dev/null || true

                    echo "Starting MySQL container..."
                    docker rm -f mysql-db 2>/dev/null || true
                    docker run -d --name mysql-db --network shopflow-net \
                        -e MYSQL_ROOT_PASSWORD=ShopFlow@123 \
                        -e MYSQL_DATABASE=shopflow \
                        mysql:8

                    echo "Waiting for MySQL to be ready..."
                    until docker exec mysql-db mysqladmin ping -h "localhost" --silent; do
                        sleep 5
                    done

                    echo "Stopping old app container..."
                    docker rm -f ${CONTAINER} 2>/dev/null || true

                    echo "Starting ShopFlow app..."
                    docker run -d --restart=always -p ${PORT}:${PORT} \
                        --name ${CONTAINER} \
                        --network shopflow-net \
                        -e SPRING_DATASOURCE_URL="jdbc:mysql://mysql-db:3306/shopflow?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" \
                        -e SPRING_DATASOURCE_USERNAME=root \
                        -e SPRING_DATASOURCE_PASSWORD=ShopFlow@123 \
                        ${IMG_NAME}

                    echo "Running containers:"
                    docker ps

                    echo "App logs:"
                    docker logs ${CONTAINER}
                '''
            }
        }
    }

    post {
        success {
            echo "✅ SUCCESS: App running at http://localhost:9090"
        }
        failure {
            echo "❌ FAILED: Check logs above carefully"
        }
    }
}