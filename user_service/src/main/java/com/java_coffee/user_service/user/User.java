package com.java_coffee.user_service.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java_coffee.user_service.helpers.Argon2PasswordEncoder;
import com.java_coffee.user_service.user.constants.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "_users")
public class User {

    // right now, generating salts, hashing and verifying is done by the User object
    @Transient
    private Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "user_name", nullable = false, length = 35, unique = true)
    @NotEmpty(message = "User name cannot be left empty.")
    @NotBlank(message = "User name cannot be left blank.")
    @Size(max = 35, message = "User name cannot be longer than 35 characters.")
    private String userName;

    @Column(name = "user_type", nullable = false)
    private Role role;

    @Column(name = "email_address", nullable = false, length = 50, unique = true)
    @Email
    @NotEmpty(message = "Email address cannot be left empty.")
    @NotBlank(message = "Email address cannot be left blank.")
    @Size(max = 35, message = "Email address cannot be longer than 35 characters.")
    private String emailAddress;

    @Column(name = "first_name", nullable = true, length = 35)
    @Size(min = 1, max = 35, message = "First name cannot be longer than 35 characters.")
    private String firstName;

    @Column(name = "last_name", nullable = true, length = 35)
    @Size(min = 1, max = 35, message = "Last name cannot be longer than 35 characters.")
    private String lastName;

    @Column(name = "password_hash", nullable = false, length = 344)
    @NotEmpty(message = "Password cannot be left empty.")
    @NotBlank(message = "Password cannot be left blank.")
    private String passwordHash;

    @Column(name = "salt", nullable = false, length = 88)
    private String salt;

    @Column(name = "join_date")
    @CreationTimestamp
    @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
    private LocalDateTime joinDate;

    @Column(name = "is_banned", nullable = false, columnDefinition = "boolean default false")
    private boolean isBanned;

    @Column(name = "is_suspended", nullable = false, columnDefinition = "boolean default false")
    private boolean isSuspended;

    @Column(name = "suspension_date")
    private LocalDateTime suspensionDate;

    @Column(name = "suspension_expiration")
    private LocalDateTime suspensionExpiration;

    @Column(name = "is_confirmed", nullable = false, columnDefinition = "boolean default false")
    private boolean isConfirmed;

    // user needs a list of orders once I talk with Ajmal about doing this

    protected User() {}

    protected User(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.role = Role.USER;
        this.salt = encoder.generateSalt();
        this.passwordHash = encoder.hash(password, salt);
        this.joinDate = LocalDateTime.now();
        this.isBanned = false;
        this.isSuspended = false;
        this.isConfirmed = false;
    }

    protected User(User source) {
        this.userId = source.userId;
        this.userName = source.userName;
        this.role = source.role;
        this.emailAddress = source.emailAddress;
        this.firstName = source.firstName;
        this.lastName = source.lastName;
        this.passwordHash = source.passwordHash;
        this.salt = source.salt;
        this.joinDate = source.joinDate;
        this.isBanned = source.isBanned;
        this.isSuspended = source.isSuspended;
        this.isConfirmed = source.isConfirmed;
    }

    // for mapping to the DTO
    protected User (long userdId, String userName, Role userType, String emailAddress, String firstName, String lastName, LocalDateTime joinDate, boolean isBanned, boolean isSuspended, LocalDateTime suspensionDate, LocalDateTime suspensionExpiration, boolean isConfirmed) {
        this.userId = userdId;
        this.userName = userName;
        this.role = userType;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = null;
        this.salt = null;
        this.joinDate = joinDate;
        this.isBanned = isBanned;
        this.isSuspended = isSuspended;
        this.suspensionDate = suspensionDate;
        this.suspensionExpiration = suspensionExpiration;
        this.isConfirmed = isConfirmed;
    }

    protected long getUserId() {
        return userId;
    }

    protected String getUserName() {
        return userName;
    }

    protected void setUserName(String userName) {
        this.userName = userName;
    }

    protected Role getRole() {
        return role;
    }

    protected void setRole(Role role) {
        this.role = role;
    }

    protected String getEmailAddress() {
        return emailAddress;
    }

    protected void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    protected String getFirstName() {
        return firstName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected String getPasswordHash() {
        return passwordHash;
    }

    protected void setPasswordHash(String password) {
        this.passwordHash = encoder.hash(password, salt);
    }

    protected void resetSalt() {
        this.salt = encoder.generateSalt();
    }

    protected LocalDateTime getJoinDate() {
        return joinDate;
    }

    protected boolean getIsBanned() {
        return isBanned;
    }

    protected void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    protected void banHammer() {
        isBanned = true;
    }

    // this would need a timestamp and a way to automatically list suspensions after a given period of time
    protected boolean getIsSuspended() {
        return isSuspended;
    }

    protected void setIsSuspended(boolean isSuspended) {
        if (isSuspended) {
            this.suspensionDate = LocalDateTime.now();
        }
        this.isSuspended = isSuspended;
    }

    protected LocalDateTime getSuspensionDate() {
        return suspensionDate;
    }

    protected LocalDateTime getSuspensionExpiration() {
        return suspensionExpiration;
    }

    protected boolean getIsConfirmed() {
        return isConfirmed;
    }

    protected void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    // Verify if the password used to logon matches the hash
    protected boolean verifyPassword(String password) {
        return encoder.verify(password, salt, passwordHash);
    }

    // fix this so it's not giving out sensitive data once I no longer need to make sure it's actually hashing passwords and making salts properly
    @Override
    public String toString() {
        return "user id: " + userId + " user name: " + userName + "\n\tpassword hash: " + passwordHash + "\n\tsalt: " + salt
            + "\n\temail address: " + emailAddress
            + "\n\tuser type: " + role.toString()
            + "\n\tfirst name: " + firstName
            + "\n\tlast name: " + lastName
            + "\n\tis user banned: " + isBanned
            + "\n\tis user suspended: " + isSuspended
            + "\n\tis email confirmed: " + isConfirmed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + ((joinDate == null) ? 0 : joinDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (role != other.role)
            return false;
        if (emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        } else if (!emailAddress.equals(other.emailAddress))
            return false;
        if (joinDate == null) {
            if (other.joinDate != null)
                return false;
        } else if (!joinDate.equals(other.joinDate))
            return false;
        return true;
    }

    
    
}
