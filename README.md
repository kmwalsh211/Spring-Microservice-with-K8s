# my-spring-container

## Purpose
This project is a full-stack application consisting of a **Spring Boot microservice backend** and a **React frontend**. The two services are containerized with **Docker** and deployed together on a local **Kubernetes cluster** (via Docker Desktop).

---

## Prerequisites
Before running this project, ensure you have:
- Docker Desktop installed
- Kubernetes enabled in Docker Desktop settings
- A DockerHub account

---

## How to Build

### 1. Download the repository to your local machine

### 2. Build the containers

#### Backend
From the project root `demo/`:

```bash
# Build the JAR
./gradlew clean bootJar

# Build the Docker image
docker build -t <your-dockerhub-username>/spring-microservice:3.0.2 .
```

#### Frontend
From `demo/frontend/`:

```bash
# Install dependencies
npm install

# Build production bundle
npm run build

# Build the Docker image
docker build -t <your-dockerhub-username>/react-frontend:2.0.0 .
```

### 3. Push to DockerHub

```bash
docker login

docker push <your-dockerhub-username>/spring-microservice:3.0.2
docker push <your-dockerhub-username>/react-frontend:2.0.0
```

### 4. Deploy to Kubernetes

From `demo/`:

```bash
# Deploy the manifests
kubectl apply -f K8s/spring-deployment.yaml
kubectl apply -f K8s/spring-service.yaml
kubectl apply -f K8s/react-deployment.yaml
kubectl apply -f K8s/react-service.yaml

# Check status
kubectl get all
```

Wait until both React and Spring pods are in Running state.

### 5. Access the React UI

Get the NodePort of the React service:

```bash
kubectl get service react-service
```

Access the UI at:

```
http://localhost:<NodePort>
```

### 6. Test CRUD operations

Once in the UI:

- **Create** → Add a new movie by entering Title + Year.
- **Read** → All movies are listed in the table.
- **Update** → Click Edit, change fields, and save.
- **Delete** → Click Delete to remove a movie.

Changes are reflected immediately in the table.

### 7. Clean up

```bash
# To remove all resources from your cluster:
kubectl delete -f spring-deployment.yaml
kubectl delete -f spring-service.yaml
kubectl delete -f react-deployment.yaml
kubectl delete -f react-service.yaml

# Verify:
kubectl get all
```

You should no longer see the deployments or services.

---

## Notes

- Since the backend uses in-memory storage, scaling Spring Boot to multiple replicas (replicas: 2) will result in inconsistent state between pods. For this demo, keep replicas at 1.

- In production, this would be solved by connecting Spring Boot to a persistent database (Postgres, MySQL, etc.).