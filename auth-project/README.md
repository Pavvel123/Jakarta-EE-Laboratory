# Simple RPG Auth

Simple RPG auth server infrastructure service.

[![MIT licensed][shield-mit]](LICENSE)
[![Java v21][shield-java]](https://openjdk.java.net/projects/jdk/21/)
[![Spring Framework v6][shield-spring]](https://spring.io/projects/spring-framework)
[![Spring Boot v3][shield-spring-boot]](https://spring.io/projects/spring-boot)
[![Jakarta EE v10][shield-jakarta]](https://jakarta.ee/specifications/platform/10/)
[![Apache Maven v3][shield-maven]](https://maven.apache.org/index.html)

## Requirements

The list of tools required to build and run the project:

* Open JDK 25
* Apache Maven 3.8

## Building

To build maven projects, use:

```bash
mvn clean package
```

If your default `java` is not from JDK 21, use (change `JAVA_HOME` path accordingly to your system):

```bash
JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 mvn package
```

## Running

To run java projects, use:

```bash
java -jar target/simple-rpg-monolith-2.0.0-SNAPSHOT.jar 
```

## License

The source code in this repository is licensed under the [MIT License](./LICENSE).

## Author

Copyright &copy; 2020 - 2026, Michał (psysiu) Wójcik

[![][gravatar-psysiu]]()

[shield-mit]: https://img.shields.io/badge/license-MIT-blue.svg?logo=opensourceinitiative
[shield-java]: https://img.shields.io/badge/Java-21-blue.svg?logo=openjdk
[shield-spring]: https://img.shields.io/badge/Spring%20Framework-6-blue.svg?logo=spring
[shield-spring-boot]: https://img.shields.io/badge/Spring%20Boot-3-blue.svg?logo=springboot
[shield-jakarta]: https://img.shields.io/badge/Jakarta_EE-10-blue.svg
[shield-maven]: https://img.shields.io/badge/Apache_Maven-3-blue.svg?logo=apachemaven
[gravatar-psysiu]: https://s.gravatar.com/avatar/b61b36a5b97ca33e9d11d122c143b9f0
