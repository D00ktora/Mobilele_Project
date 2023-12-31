package org.softuni.mobilele.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.softuni.mobilele.model.dto.validator.passwordValidator.PasswordValidator;
import org.softuni.mobilele.model.dto.validator.unicEmail.EmailValidator;

@PasswordValidator(
        first = "password",
        second = "confirmPassword",
        message = "Password do not match."
)
public class UserRegistrationDTO {
        @NotNull
        @NotEmpty
        String firstName;
        @NotNull
        @NotEmpty
        String lastName;
        @NotEmpty(message = "Email should be provided!")
        @Email(message = "Email should be valid")
        @EmailValidator(message = "Email address already exist.")
        @Size(min = 2, message = "")
        String email;
        @NotNull
        @NotEmpty
        String password;
        @NotNull
        @NotEmpty
        String confirmPassword;

        public String getFirstName() {
                return firstName;
        }

        public UserRegistrationDTO setFirstName(String firstName) {
                this.firstName = firstName;
                return this;
        }

        public String getLastName() {
                return lastName;
        }

        public UserRegistrationDTO setLastName(String lastName) {
                this.lastName = lastName;
                return this;
        }

        public String getEmail() {
                return email;
        }

        public UserRegistrationDTO setEmail(String email) {
                this.email = email;
                return this;
        }

        public String getPassword() {
                return password;
        }

        public UserRegistrationDTO setPassword(String password) {
                this.password = password;
                return this;
        }

        public String getConfirmPassword() {
                return confirmPassword;
        }

        public UserRegistrationDTO setConfirmPassword(String confirmPassword) {
                this.confirmPassword = confirmPassword;
                return this;
        }
}
