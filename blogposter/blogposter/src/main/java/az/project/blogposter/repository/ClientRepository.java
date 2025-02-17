package az.project.blogposter.repository;

import az.project.blogposter.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Integer> {

}
