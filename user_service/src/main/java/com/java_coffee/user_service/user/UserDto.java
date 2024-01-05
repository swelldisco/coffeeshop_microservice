package com.java_coffee.user_service.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java_coffee.user_service.user.constants.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    
    private long userId;

    @NonNull
    @NotEmpty(message = "User name cannot be left empty.")
    @NotBlank(message = "User name cannot be left blank.")
    @Size(max = 35, message = "User name cannot be longer than 35 characters.")
    private String userName;

    private UserType userType;

    @Email
    @NotEmpty(message = "Email address cannot be left empty.")
    @NotBlank(message = "Email address cannot be left blank.")
    @Size(max = 50, message = "Email address cannot be longer than 35 characters.")
    private String emailAddress;

    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;

    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;

    @NotEmpty(message = "Password cannot be left empty.")
    @NotBlank(message = "Password cannot be left blank.")
    @Size(max = 35, message = "Password cannot be longer than 35 characters.")
    private String password;

    @PastOrPresent
    @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
    private LocalDateTime joinDate;

    private boolean isBanned;

    private boolean isSuspended;

    private LocalDateTime suspensionDate;

    private LocalDateTime suspensionExpiration;

    private boolean isConfirmed;

    // constructor for registering
    public UserDto(String username, String emailAddress, String password) {
        this.userName = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userType = UserType.USER;
    }

    // for mapping 
    public UserDto (long userdId, String userName, UserType userType, String emailAddress, String firstName, String lastName, LocalDateTime joinDate, boolean isBanned, boolean isSuspended, LocalDateTime suspensionDate, LocalDateTime suspensionExpiration, boolean isConfirmed) {
        this.userId = userdId;
        this.userName = userName;
        this.userType = userType;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.joinDate = joinDate;
        this.isBanned = isBanned;
        this.isSuspended = isSuspended;
        this.suspensionDate = suspensionDate;
        this.suspensionExpiration = suspensionExpiration;
        this.isConfirmed = isConfirmed;
    }

    protected boolean getIsBanned() {
        return isBanned;
    }

    protected void setIsBanned(boolean isBanned) {
        if (!isBanned) {
            this.isBanned = isBanned;
        }
    }

    protected boolean getIsSuspended() {
        return isSuspended;
    }

    protected void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    protected boolean getIsConfirmed() {
        return isConfirmed;
    }

    protected void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
 
}
