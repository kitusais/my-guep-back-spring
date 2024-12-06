##
## Build Stage
##
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers du projet
COPY pom.xml ./
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

##
## Runtime Stage
##
FROM eclipse-temurin:17-jre-alpine

# Créer un utilisateur non-root pour des raisons de sécurité
RUN addgroup -S appuser && adduser -S -G appuser appuser

# Définir le répertoire de travail
WORKDIR /app

# Copier l'application depuis l'étape de build
COPY --from=build /app/target/my-guep-0.0.1-SNAPSHOT.jar /app/my-guep.jar
# Créer le répertoire de logs
RUN mkdir -p /var/log/my-guep

# Changer le propriétaire des fichiers
RUN chown -R appuser:appuser /app
RUN chown -R appuser:appuser /var/log/my-guep

# Passer à l'utilisateur non-root
USER appuser

# Commande par défaut
CMD ["java", "-jar", "/app/my-guep.jar"]

