package springloggenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springloggenerator.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

	Genre findByName(String name);

}
