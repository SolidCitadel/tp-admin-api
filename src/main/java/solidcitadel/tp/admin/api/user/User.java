package solidcitadel.tp.admin.api.user;

import jakarta.persistence.*;
import lombok.*;
import solidcitadel.tp.admin.api.plan.Plan;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Plan> plans = new ArrayList<>();

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static User createUser(String username, String password, UserRoleEnum role){
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

}