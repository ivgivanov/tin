# TIN
The Teammate I Need (TIN)

Development requirements:
* JDK 8
* vim25-7.0.0-15878048.jar in the local maven repository. It can be added with `mvn install:install-file -Dfile=/path-to/vim25-7.0.0-15878048.jar -DgroupId=com.vmware -DartifactId=vim25 -Dversion=7.0.0-15878048 -Dpackaging=jar` Note: the local Maven repository defined in pom.xml may differ for your OS

During development, run from IDE or `mvn spring-boot:run` 

Build with: `mvn clean package spring-boot:repackage`
Start with: `java -jar tin-XXX.jar`