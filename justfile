# build the project
build:
   mvn -DskipTests clean package

# registry server docker build
registry_jib: build
   mvn -pl spring-boot-dubbo-registry jib:dockerBuild

# run dubbo registry server
zookeeper:
  docker-compose up -d

# run dubbo server
server: build zookeeper
  java -jar -Xmx1G -Xms1G spring-boot-dubbo-server/target/spring-boot-dubbo-server-1.0.0-SNAPSHOT.jar