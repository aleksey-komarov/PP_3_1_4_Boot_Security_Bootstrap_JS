package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;



@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]*$", message = "Имя должно содержать только буквы.")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 4 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]*$", message = "Фамилия должна содержать только буквы.")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 4 символов")
    @Column(name = "lastname")
    private String lastname;

    @NotEmpty(message = "Поле не может быть пустым")
    @Email(message = "Email должен быть корректным")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 4, max = 10, message = "Логин должн содержать от 4 до 10 символов")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 4, message = "Минимальная длинна пароля 4 символа")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(long id, String name, String lastname, String email, String username, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
