package ro.ovidiudrumia.fileuploadweb.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import ro.ovidiudrumia.fileuploadweb.model.User;
import ro.ovidiudrumia.fileuploadweb.service.FileUploadUserDetailsService;

/**
 * @author Ovidiu Drumia
 */
public interface UserRepository extends GraphRepository<User>,
        RelationshipOperationsRepository<User>, FileUploadUserDetailsService {

    User findByLogin(String login);
}
