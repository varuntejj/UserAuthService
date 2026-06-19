package in.varun.userauthservice.Service;

import in.varun.userauthservice.Exceptions.IncorrectPasswordException;
import in.varun.userauthservice.Exceptions.UserAlreadyExistException;
import in.varun.userauthservice.Exceptions.UserNotRegisteredException;
import in.varun.userauthservice.Models.RoleModel;
import in.varun.userauthservice.Models.State;
import in.varun.userauthservice.Models.UserModel;
import in.varun.userauthservice.Repositores.RoleRepo;
import in.varun.userauthservice.Repositores.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServieve implements IAuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserModel signup(String name, String email, String password) {
        System.out.println("Signup called with name: " + name + ", email: " + email);
        /*
        every user should register with a unique email
         */
        Optional<UserModel> optionalUser = userRepo.findByEmail(email);

        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException("Please try different email id");
        }

        UserModel user = new UserModel();
        user.setEmail(email);
        user.setName(name);
//        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setLastUpdatedAt(new Date());
        user.setState(State.ACTIVE);

        /*
        what else to set?
        Be default, user role is DEFAULT
         */
        RoleModel role;
        Optional<RoleModel> optionalRole = roleRepo.findByName("DEFAULT");

        if(optionalRole.isEmpty()){
            role = new RoleModel();
            role.setName("DEFAULT");
            role.setCreatedAt(new Date());
            role.setLastUpdatedAt(new Date());
            role.setState(State.ACTIVE);
            roleRepo.save(role);
        }else{
            role = optionalRole.get();
        }

        List<RoleModel> roles = new ArrayList<>();
        roles.add(role);

        user.setRoles(roles);

        /*
        Publish a message to the queue
         */

        return userRepo.save(user);
    }

    @Override
    public UserModel login(String email, String password) {
        Optional<UserModel> optionalUserModel=userRepo.findByEmail(email);
        if(optionalUserModel.isEmpty())
        {
            throw new UserNotRegisteredException("Please regester first");
        }
        UserModel userModel=optionalUserModel.get();
        if(password.equals(userModel.getPassword()))
        {
            return userModel;
        }
        else throw new IncorrectPasswordException("Incorrect Password");
//        return null;
    }
}
