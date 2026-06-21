package in.varun.userauthservice.Pojos;

import in.varun.userauthservice.Models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserTokens {
    private UserModel user;
    private String token;

}
