package project.shortlink.repository;

import project.shortlink.entity.Link;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataDynamoLinkRepository extends CrudRepository<Link, String> {
    @EnableScan
    Optional<Link> findByOriginalUrl(String originalUrl);
    @EnableScan
    List<Link> findAll();
    @EnableScan
    void deleteAll();
}
