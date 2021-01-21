package slyDev;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.bind.annotation.CrossOrigin;
import slyDev.model.Role;
import slyDev.model.User;
import slyDev.service.UserService;

@CrossOrigin
@SpringBootApplication
public class RegistrationApi implements CommandLineRunner {

  @Autowired
  UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(RegistrationApi.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public void run(String... params) throws Exception {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword("admin");
    admin.setEmail("admin@email.com");
    admin.setDob(OffsetDateTime.of(1980, 4, 12, 20, 15, 45, 345875000, ZoneOffset.of("+07:00")));
    admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

    userService.signup(admin);

    User client = new User();
    client.setUsername("client");
    client.setPassword("client");
    client.setEmail("client@email.com");
    client.setDob(OffsetDateTime.of(1981, 4, 12, 20, 15, 45, 345875000, ZoneOffset.of("+07:00")));
    client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

    userService.signup(client);

    User client2 = new User();
    client2.setUsername("client2");
    client2.setPassword("client2");
    client2.setEmail("client2@email.com");
    client2.setDob(OffsetDateTime.of(1982, 4, 12, 20, 15, 45, 345875000, ZoneOffset.of("+07:00")));
    client2.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

    userService.signup(client2);
  }

}
