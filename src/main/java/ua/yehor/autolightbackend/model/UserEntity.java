package ua.yehor.autolightbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Represents a user entity within the system.
 * Implements UserDetails for Spring Security integration.
 */
@Entity
@Table(name = "_user")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity implements UserDetails {
    /**
     * Unique identifier for the user entity.
     */
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    /**
     * User's login name.
     */
    private String login;

    /**
     * User's password.
     */
    @JsonIgnore
    private String password;

    /**
     * Date and time of user creation.
     */
    private LocalDateTime createdAt;

    /**
     * User's email address.
     */
    @Email
    private String email;

    /**
     * User's phone number.
     */
    private String phone;

    /**
     * Set of roles associated with the user.
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * Set of devices associated with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeviceEntity> devices;

    /**
     * Constructs a UserEntity object with a login name and password.
     *
     * @param login    The user's login name.
     * @param password The user's password.
     */
    public UserEntity(String login, String password) {
        this.login = login;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.roles = Set.of(Role.USER);
        this.devices = Set.of();
    }

    // Methods related to UserDetails

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return Collection of authorities (roles) associated with the user.
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Retrieves the username used to authenticate the user.
     *
     * @return The user's login name.
     */
    @Override
    @JsonIgnore
    public String getUsername() {
        return login;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return True if the user's account is valid, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return True if the user is not locked, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return True if the user's credentials are valid, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return True if the user is enabled, false otherwise.
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    /**
     * Checks if the user contains a specific role.
     *
     * @param admin The role to check.
     * @return True if the user has the role, false otherwise.
     */
    public boolean containsRole(Role admin) {
        return roles.contains(admin);
    }

    /**
     * Adds a role to the user's set of roles.
     *
     * @param newRole The role to be added.
     */
    public void addRole(Role newRole) {
        roles.add(newRole);
    }

    /**
     * Removes a role from the user's set of roles.
     *
     * @param deletingRole The role to be removed.
     */
    public void removeRole(Role deletingRole) {
        roles.remove(deletingRole);
    }

    /**
     * Adds a device to the user's set of devices.
     *
     * @param device The device to be added.
     */
    public void addDevice(DeviceEntity device) {
        devices.add(device);
    }

    /**
     * Removes a device from the user's set of devices.
     *
     * @param device The device to be removed.
     */
    public void removeDevice(DeviceEntity device) {
        devices.remove(device);
    }
}
