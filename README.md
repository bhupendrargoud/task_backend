# Web Application Development Steps
# Backend CI CD pipe line using Jenkins and kubernates

## Installing  the softwares
Run below cmd with sudo privelage on ubuntu

```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
sudo apt install maven -y
sudo apt install nodejs -y
sudo apt install npm -y
sudo apt install docker.io -y

#Install jenkins

sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update
sudo apt-get install jenkins -y

# Install Kubernetes

sudo apt-get install -y apt-transport-https ca-certificates curl
# If the folder `/etc/apt/keyrings` does not exist, it should be created before the curl command, "sudo mkdir -p -m 755 /etc/apt/keyrings"
curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.29/deb/Release.key | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg
echo 'deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.29/deb/ /' | sudo tee /etc/apt/sources.list.d/kubernetes.list
sudo apt-get update
sudo apt-get install -y kubectl

#install minikube

curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

```


## Configure docker with jenkins

```bash
sudo systemctl start docker
sudo systemctl start jenkins
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins
groups jenkins
```
```bash
#login to docker hub to push images
 docker login -u USERNAME -p PASSWORD

```
```bash
#jenkins initial password
sudo cat /var/lib/jenkins/secrets/initialAdminPassword

```
### On jenkins server
After login :-
Install and configure the necessary Jenkins plugins to enable Docker integration. The key plugins include:

`Docker Plugin`: This plugin allows Jenkins to interact with Docker.
`Docker Pipeline`: Adds Docker-based steps in Jenkins Pipeline.
Install these plugins through the Jenkins dashboard under `"Manage Jenkins" > "Manage Plugins" > "Available" `tab.

## Adding git credentilas


If you haven't already, generate a Personal Access Token from your Git provider (e.g., GitHub, GitLab).
Make sure to grant the necessary permissions, such as repository access, for the token.

Navigate to the Jenkins dashboard.

Click on `Manage Jenkins` in the left sidebar.
Select `Manage Credentials.`
Under the `System` tab, click on `Global credentials (unrestricted).`
Click on `(add credentials)` on the left.
Choose the kind of credentials to add. For Git, you typically use `Secret text` or `Secret file` for a file containing the token.
Enter the Token in the `Secret` field.
set an ID, `git_jenkins`
description,`git_jenkins`
 and choose the appropriate scope.
Click `OK` to save the credentials.


## Configure Maven in Jenkins:

Go to the Jenkins dashboard.
Navigate to `Manage Jenkins` > `Global Tool Configuration.`
Find the `Maven` section.
Click on `Maven installations...`
Click on `Add Maven` and provide a name (`Maven`) and set the MAVEN_HOME let Jenkins download it for you.





## integrating kubernetes
```bash
minikube start --driver=docker
kubectl config view
#Note the server IP
```
Install  Kubernetes CLI Plugins:
In Jenkins, go to `Manage Jenkins` -> `Manage Plugins` -> `Available` tab.

Configure Kubernetes Cluster:
Configure kubectl with the credentials for your Kubernetes cluster on the Jenkins server. This usually involves copying the Kubernetes configuration file (kubeconfig) to the appropriate location. The kubeconfig file typically resides in `~/.kube/config `by default.
```bash
# Copy the kubeconfig file from your local machine to the Jenkins server
scp ~/.kube/config /var/lib/jenkins/.kube/config

```

Create Kubernetes Service Account:
To interact with the Kubernetes cluster from Jenkins, it's a good practice to create a Kubernetes service account. This account should have appropriate RBAC (Role-Based Access Control) permissions for the tasks Jenkins will perform.

### Create a Kubernetes Credential in Jenkins:

Open Jenkins and navigate to `Manage Jenkins` > `Manage Credentials.`
Click on `(global)` domain or the domain relevant to your setup.
Click on `Add Credentials.`
Choose `Secret text` as the kind and enter the contents of the kubeconfig file 
provide a unique ID `minikube` and description `minikube_jenkins`for the credential.
Click `OK` to save the credential.
## Create a New Jenkins Job:

From the Jenkins dashboard, click on `New Item` to create a new Jenkins job.  
Choose `Pipeline` as the job type.
Ener the name `Backend_pipeline`
Configure Pipeline from SCM (Source Code Management):
In the job configuration, find the `Pipeline` section.
Choose `Pipeline script from SCM` as the Definition.
Select `Git` as the SCM.
Enter the URL 
```bash
https://github.com/gavika/reference-app-payroll-backend.git
```
specify credentials `git_jenkins`

#### Enable Poll SCM:

In the job configuration page, find the `Build Triggers` section.
Check the box next to `Poll SCM.`
In the `Schedule` field, enter `* * * * *`  
Save your Jenkins job configuration.  

Run the Jenkins job manually for the first time to verify that the pipeline is correctly configured.
