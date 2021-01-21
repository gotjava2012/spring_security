package slyDev.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
public class User {

  @Setter(AccessLevel.NONE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Size(min = 8, message = "Minimum password length: 8 characters")
  private String password;

  @Column(name = "dob", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
  private OffsetDateTime dob;

  @Column(name = "createdDate", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
  final private OffsetDateTime createDate = OffsetDateTime.now();

  @ElementCollection(fetch = FetchType.EAGER)
  List<Role> roles;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private OffsetDateTime lastModifiedDate;

}
