package in.varun.userauthservice.Dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDTO {
    private String email;
    private String name;
    private String password;
}
