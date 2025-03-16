pipeline {
    agent any

    environment {
        IMAGE_NAME = "fulltestingdemo/myapp"
        IMAGE_TAG = "latest"
        KUBE_CONFIG = credentials('kubeconfig')  // Kubernetes config
        PREVIOUS_IMAGE_TAG = "previous"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/your-repo.git'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Run Integration Tests') {
            steps {
                sh 'mvn verify'
            }
        }

        stage('Build and Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Backup Previous Image') {
            steps {
                script {
                    sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${IMAGE_NAME}:${PREVIOUS_IMAGE_TAG}"  // Save the old image in case of rollback
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh 'kubectl apply -f k8s/'
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    def status = sh(script: "kubectl get pods --selector=app=myapp --field-selector=status.phase=Running | wc -l", returnStdout: true).trim()
                    if (status.toInteger() == 0) {
                        error("Deployment failed, rolling back...")
                    }
                }
            }
        }
    }

    post {
        success {
            echo "🚀 Deployment Successful!"
        }
        failure {
            script {
                echo "❌ Deployment Failed! Rolling back to the previous version..."
                sh "docker tag ${IMAGE_NAME}:${PREVIOUS_IMAGE_TAG} ${IMAGE_NAME}:${IMAGE_TAG}"
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh 'kubectl rollout undo deployment/myapp'
                }
            }
        }
    }
}
