package in.varun.userauthservice.Service;

import in.varun.userauthservice.Models.UserModel;
import in.varun.userauthservice.Pojos.UserTokens;

public interface IAuthService {
    UserModel signup(String name, String email, String password);
    UserTokens login(String email, String password);
}
