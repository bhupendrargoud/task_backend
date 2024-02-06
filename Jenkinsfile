pipeline {
    agent any
    
    environment {
        GIT_CREDENTIALS = credentials('git_jenkins')
        DOCKER_REGISTRY_CREDENTIALS = credentials('docker_jenkins')
        DOCKER_IMAGE_NAME = 'bhupendrargoud/payroll_app'
        DOCKER_IMAGE_TAG = 'latest'
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    git credentialsId: "${GIT_CREDENTIALS}", url: 'https://github.com/gavika/reference-app-payroll-backend.git'
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    def mvnHome = tool 'Maven'
                    sh "${mvnHome}/bin/mvn clean package"
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t bhupendrargoud/payroll_app ."
                    sh "docker push bhupendrargoud/payroll_app:latest"
                }
            }
        }
        
        stage('Deploy Jar on K8s') {
            steps {
                // Deploy the JAR on Kubernetes
                withKubeConfig(credentialsId: 'minikube', serverUrl: 'https://192.168.49.2:8443') {
                    sh "kubectl apply -f jar-deployment.yaml"
                }
            }
        }
    }
    
    post {
        success {
            // Archive the built JAR file
            archiveArtifacts 'target/*.jar'
        }
    }
}
