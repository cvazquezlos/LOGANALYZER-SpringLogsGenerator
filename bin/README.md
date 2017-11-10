# LOGANALYZER - Spring Logs Generator

## Getting started with this tool
This tool has been created for providing logs to LOGANALYZER tool. These logs follow the Logback format. To generate the complete output:
**PRE**. Ensure `JAVA_HOME` environment variable is se and points to your JDK installation directory. Follow these steps:
1. Go to Apache Maven Project [download](https://maven.apache.org/download.cgi) page and download the Binary tar.gz archive.
2. Extract it to the directory you wish to install Maven. The best way to do it is inside Program Files directory.
3. Set the environment variables: `M2_HOME=C:\Program Files\apache-maven-version`, `M2=%M2_HOME%\bin`.
4. Append the string `%M2` to the end of the system variable *Path*.
5. Open Command Console and type `mvn --version`.
6. Clone or download this repository.
7. Open the Command Console and navigate inside project. Then, execute `mvn clean install --log-file log.txt` to save all the information about the test.

This tool works with LOGANALYZER and ElasticsearchDataUpdater repositories.
