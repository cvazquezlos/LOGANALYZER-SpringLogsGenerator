FROM java:8
CMD ["mvn", "clean, "test, "--log-file, "log.txt"]