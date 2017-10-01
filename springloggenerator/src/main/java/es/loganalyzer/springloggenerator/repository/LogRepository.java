package es.loganalyzer.springloggenerator.repository;

import es.loganalyzer.springloggenerator.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogRepository extends ElasticsearchRepository<Log, String> {

	Page<Log> findByTimeStamp(String timeStamp, Pageable pageable);

	List<Log> findByTimeStamp(String timeStamp);

}