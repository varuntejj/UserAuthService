package in.varun.userauthservice.Service;

import in.varun.userauthservice.Models.UserModel;

public interface IAuthService {
    UserModel signup(String name, String email, String password);
    UserModel login(String email, String password);
}
