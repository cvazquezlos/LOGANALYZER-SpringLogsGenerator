package es.loganalyzer.springloggenerator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "loganalyzer", type = "logs")
public class Log {

	@Id
	private String id;
	private String entireLog;
	private String timeStamp;
	private String threadName;
	private String level;
	private String loggerName;
	private String formattedMessage;

	public Log() {
	}

	public Log(String id, String entireLog, String timeStamp, String threadName, String level, String loggerName,
			String formattedMessage) {
		this.id = id;
		this.entireLog = entireLog;
		this.timeStamp = timeStamp;
		this.threadName = threadName;
		this.level = level;
		this.loggerName = loggerName;
		this.formattedMessage = formattedMessage;
	}

	@Override
	public String toString() {
		return "Log{" + "id='" + id + '\'' + ", entireLog='" + entireLog + '\'' + ", timeStamp='" + timeStamp + '\''
				+ ", threadName='" + threadName + '\'' + ", level='" + level + '\'' + ", loggerName='" + loggerName
				+ '\'' + ", formattedMessage='" + formattedMessage + '\'' + '}';
	}
}