FROM java:8
EXPOSE 8443
CMD ["mvn", "clean, "test, "--log-file, "log.txt"]