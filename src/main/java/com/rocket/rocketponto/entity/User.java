package com.rocket.rocketponto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String position;

    @Column(length = 50)
    private String department;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PointRecord> pointRecord;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<PointRecord> getPointRecord() {
        return pointRecord;
    }

    public void setPointRecord(List<PointRecord> pointRecord) {
        this.pointRecord = pointRecord;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna os papéis (roles) do usuário. Se não houver roles, retorna uma lista vazia.
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        // O Spring Security usa o email como nome de usuário (username) para autenticação.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Verifica se a conta do usuário não expirou.
        return true; // Ou implemente lógica personalizada, se necessário.
    }

    @Override
    public boolean isAccountNonLocked() {
        // Verifica se a conta do usuário não está bloqueada.
        return true; // Ou implemente lógica personalizada, se necessário.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Verifica se as credenciais do usuário não expiraram.
        return true; // Ou implemente lógica personalizada, se necessário.
    }

    @Override
    public boolean isEnabled() {
        // Verifica se o usuário está ativo.
        return this.active;
    }
}
