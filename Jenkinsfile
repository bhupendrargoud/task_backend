pipeline {
    agent any

    stages {
        stage("git checkout") {
            steps {
                git branch: 'main', credentialsId: 'git_jenkins', url: 'https://github.com/gavika/reference-app-payroll-backend.git'
                }
            }
        stage('Build') {
            steps {
                script {
                    def mvnHome = tool 'Maven'
                    sh "${mvnHome}/bin/mvn clean install -DskipTests"
                    }
                }
            }
        stage('Debug') {
            steps {
                script {
                    echo "Workspace: ${WORKSPACE}"
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t bhupendrargoud/payroll_app ."
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    sh "docker push bhupendrargoud/payroll_app:latest"
                }
            }
        }
        stage('Deploy  on k8s') {
            steps {
                withKubeConfig(credentialsId: 'minikube', serverUrl: 'https://192.168.49.2:8443') {
                    sh "kubectl apply -f mysql-pvc.yaml"
                    sh "kubectl apply -f mysql-deployment.yaml"
                    sh "kubectl apply -f mysql-service.yaml"
                    sh "kubectl apply -f jar-deployment.yaml"
                    sh "kubectl apply -f jar-service.yaml"
                }
            }
        }
    }
}


