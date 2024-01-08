package com.java_coffee.user_service.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java_coffee.user_service.helpers.Argon2PasswordEncoder;
import com.java_coffee.user_service.user.constants.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;

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
    private String userName;

    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "email_address", nullable = false, length = 50, unique = true)
    @Email
    private String emailAddress;

    @Column(name = "first_name", nullable = true, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = true, length = 50)
    private String lastName;

    @Column(name = "password_hash", nullable = false, length = 344)
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

    protected User() {}

    protected User(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.userType = UserType.USER;
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
        this.userType = source.userType;
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
    protected User (long userdId, String userName, UserType userType, String emailAddress, String firstName, String lastName, LocalDateTime joinDate, boolean isBanned, boolean isSuspended, LocalDateTime suspensionDate, LocalDateTime suspensionExpiration, boolean isConfirmed) {
        this.userId = userdId;
        this.userName = userName;
        this.userType = userType;
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

    protected UserType getUserType() {
        return userType;
    }

    protected void setUserType(UserType userType) {
        this.userType = userType;
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
        if (isBanned == true) {
            this.isBanned = isBanned;
        }
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
            + "\n\tuser type: " + userType.toString()
            + "\n\tfirst name: " + firstName
            + "\n\tlast name: " + lastName
            + "\n\tis user banned: " + isBanned
            + "\n\tis user suspended: " + isSuspended
            + "\n\tis email confirmed: " + isConfirmed;
    }
    
}
