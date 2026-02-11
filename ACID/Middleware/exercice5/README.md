# Create project

mvn archetype:generate -B "-DgroupId=fr.insa.acid" "-DartifactId=exercice5" "-DarchetypeArtifactId=maven-archetype-quickstart" "-DarchetypeVersion=1.5" "-DinteractiveMode=false"

ensure pom.xml contains the following:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>fr.insa.acid</groupId>
    <artifactId>middleware-tp</artifactId>
    <version>1.0-SNAPSHOT</version>

    <name>middleware-tp</name>
    <url>http://www.example.com</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.release>17</maven.compiler.release>
        <exec.mainClass>fr.insa.acid.Middleware</exec.mainClass> <!-- par défaut -->
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.junit</groupId>
                <artifactId>junit-bom</artifactId>
                <version>5.11.0</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-params</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <artifactId>maven-clean-plugin</artifactId>
                    <version>3.4.0</version>
                </plugin>
                <plugin>
                    <artifactId>maven-resources-plugin</artifactId>
                    <version>3.3.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.13.0</version>
                </plugin>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>3.3.0</version>
                </plugin>
                <plugin>
                    <artifactId>maven-jar-plugin</artifactId>
                    <version>3.4.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-install-plugin</artifactId>
                    <version>3.1.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-deploy-plugin</artifactId>
                    <version>3.1.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-site-plugin</artifactId>
                    <version>3.12.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-project-info-reports-plugin</artifactId>
                    <version>3.6.1</version>
                </plugin>
            </plugins>
        </pluginManagement>

        <!-- Ajout du plugin exec pour lancer les programmes facilement -->
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.3.0</version>
                <executions>
                    <execution>
                        <id>run-serveur</id>
                        <goals><goal>java</goal></goals>
                        <configuration>
                            <mainClass>fr.insa.acid.ServeurUDP</mainClass>    <!-- ← ICI -->
                        </configuration>
                    </execution>
                    <execution>
                        <id>run-middleware</id>
                        <goals><goal>java</goal></goals>
                        <configuration>
                            <mainClass>fr.insa.acid.Middleware</mainClass>    <!-- ← ICI -->
                        </configuration>
                    </execution>
                    <execution>
                        <id>run-client</id>
                        <goals><goal>java</goal></goals>
                        <configuration>
                            <mainClass>fr.insa.acid.ClientTCP</mainClass>     <!-- ← ICI -->
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

# Run project

in order :

mvn exec:java "-Dexec.mainClass=fr.insa.acid.DemarrerSysteme"
mvn exec:java "-Dexec.mainClass=fr.insa.acid.Client"
