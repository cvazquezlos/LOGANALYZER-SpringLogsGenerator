FROM java:8
EXPOSE 8443
ENTRYPOINT ["mvn clean test --log-file log.txt"]