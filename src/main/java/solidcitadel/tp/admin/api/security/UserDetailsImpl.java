package solidcitadel.tp.admin.api.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import solidcitadel.tp.admin.api.user.User;
import solidcitadel.tp.admin.api.user.UserRoleEnum;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();
        String authority = role.name();

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }
}
