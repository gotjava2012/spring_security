package slyDev.model;


import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "login_history")
public class LogInHistory {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user", nullable = false)
    private Integer userId;

    @Column( nullable = false)
    private String ip;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(name = "createdDate", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    final private OffsetDateTime createDate =OffsetDateTime.now();
}
