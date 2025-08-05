pipeline {
    agent any
   
    tools {
        maven 'Maven3'   
        jdk 'Java17'     
    }

    stages {
        stage('Checkout Code') {
            steps {
                sh 'echo passed'
                // git branch: 'main', url: 'https://github.com/YOUR_USERNAME/YOUR_REPO.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Static Code Analysis') {
            environment {
                SONAR_URL = "http://54.86.237.117:9000"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_AUTH_TOKEN')]) {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=MyProject \
                        -Dsonar.host.url=$SONAR_URL \
                        -Dsonar.login=$SONAR_AUTH_TOKEN
                    '''
                }
            }
        }
        /*
        stage('Dependency Check') {
        
            steps {
                sh '''
                    /opt/dependency-check/bin/dependency-check.sh \
                    --project Project1 \
                    --scan . \
                    --format HTML \
                    --out dependency-report.html \
                    --data ${WORKSPACE}/dc-data \
                     --noupdate
                '''
            }
        }

        stage('Publish Dependency Report') {
            steps {
                publishHTML target: [
                    reportDir: '.', 
                    reportFiles: 'dependency-report.html',
                    reportName: 'Dependency Check Report',
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ]
            }
        }
        */
        stage('Docker Build') {
            steps {
                // Stop and remove container using port 8082, if any
                    sh '''
                        CONTAINER_ID=$(docker ps -q --filter "publish=8082")
                        if [ ! -z "$CONTAINER_ID" ]; then
                            echo "Stopping existing container on port 8082: $CONTAINER_ID"
                            docker stop $CONTAINER_ID
                            docker rm $CONTAINER_ID
                        fi

                        # Remove old image if exists
                        IMAGE_ID=$(docker images -q naveen:latest)
                        if [ ! -z "$IMAGE_ID" ]; then
                            echo "Removing old image: $IMAGE_ID"
                            docker rmi -f $IMAGE_ID
                        fi
                    '''

                    // Build fresh image
                sh "docker build -t naveen:latest ."
            }
        }

        stage('Trivy Scan') {
            steps {
                sh "trivy image --scanners vuln  --cache-dir /var/lib/jenkins/.trivy-cache --severity HIGH,CRITICAL naveen:latest"
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker run -d -p 8082:3000 naveen:latest'
            }
        }
    }
}
