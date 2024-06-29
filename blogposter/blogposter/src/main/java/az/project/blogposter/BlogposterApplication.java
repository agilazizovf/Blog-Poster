package az.project.blogposter;

import az.project.blogposter.entity.Role;
//import az.project.blogposter.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogposterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogposterApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(RoleRepository roleRepo) {
//		return (args) -> {
//			Role role=new Role();
//			role.setName("ROLE_ADMIN");
//			roleRepo.save(role);
//		};
//	}

}
