package es.loganalyzer.springloggenerator.service;

import es.loganalyzer.springloggenerator.model.Log;
import es.loganalyzer.springloggenerator.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImplementation implements LogService {

    private LogRepository logRepository;

	@Autowired
    public void setBookRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log save(Log log) {
        return logRepository.save(log);
    }

    public void delete(Log log) {
    	logRepository.delete(log);
    }

    public Log findOne(String id) {
        return logRepository.findOne(id);
    }

    public Iterable<Log> findAll() {
        return logRepository.findAll();
    }

    public Page<Log> findByTimeStamp(String timeStamp, PageRequest pageRequest) {
        return logRepository.findByTimeStamp(timeStamp, pageRequest);
    }

    public List<Log> findByTimeStamp(String timeStamp) {
        return logRepository.findByTimeStamp(timeStamp);
    }

}