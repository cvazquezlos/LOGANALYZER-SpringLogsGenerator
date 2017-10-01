package es.loganalyzer.springloggenerator.service;

import es.loganalyzer.springloggenerator.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface LogService {

    Log save(Log log);

    void delete(Log log);

    Log findOne(String id);

    Iterable<Log> findAll();

    Page<Log> findByTimeStamp(String timeStamp, PageRequest pageRequest);

    List<Log> findByTimeStamp(String timeStamp);

}