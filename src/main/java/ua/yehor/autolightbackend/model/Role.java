package ua.yehor.autolightbackend.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum representing different roles in the system.
 * Implements GrantedAuthority for Spring Security integration.
 */
public enum Role implements GrantedAuthority {
    /**
     * Roles which can access different endpoints.
     */
    USER, TECHNICIAN, ADMIN;

    /**
     * Retrieves the authority of the role.
     *
     * @return The name of the role as its authority.
     */
    @Override
    public String getAuthority() {
        return this.name();
    }
}
