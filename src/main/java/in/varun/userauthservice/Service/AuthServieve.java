package in.varun.userauthservice.Service;

import in.varun.userauthservice.Exceptions.IncorrectPasswordException;
import in.varun.userauthservice.Exceptions.UserAlreadyExistException;
import in.varun.userauthservice.Exceptions.UserNotRegisteredException;
import in.varun.userauthservice.Models.RoleModel;
import in.varun.userauthservice.Models.State;
import in.varun.userauthservice.Models.UserModel;
import in.varun.userauthservice.Pojos.UserTokens;
import in.varun.userauthservice.Repositores.RoleRepo;
import in.varun.userauthservice.Repositores.SessionRepo;
import in.varun.userauthservice.Repositores.UserRepo;
import io.jsonwebtoken.Jwts;
//import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.varun.userauthservice.Models.Session;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthServieve implements IAuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SecretKey secretKey;

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
        user.setPassword(bCryptPasswordEncoder.encode(password));
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
    public UserTokens login(String email, String password) {
        Optional<UserModel> optionalUserModel=userRepo.findByEmail(email);
        if(optionalUserModel.isEmpty())
        {
            throw new UserNotRegisteredException("Please regester first");
        }
        UserModel userModel=optionalUserModel.get();
        if(bCryptPasswordEncoder.matches(password, userModel.getPassword())) {
            /*
            Generate the token
             */
            Map<String, Object> payload = new HashMap<>();
            Long nowInMills = System.currentTimeMillis(); // return timestamp in epoch
            payload.put("iat", nowInMills);
            payload.put("exp", nowInMills + 10000000); //100k milli-seconds as expiry time period
            payload.put("userId", userModel.getId());
            payload.put("iss", "scaler");
            payload.put("scope", userModel.getRoles());
            /*
            Payload is generated
             */

            String token = Jwts.builder().claims(payload).
                    signWith(secretKey).
                    compact();

            System.out.println(token.length());
            System.out.println(token);

            /*
            Create a new logged in session for the user
             */
            Session session=new Session();
            session.setToken(token);
            session.setUser(userModel);
            session.setState(State.ACTIVE);
            sessionRepo.save(session);
            /*
            We also want to return this generated token back to the client?

             */

            return new UserTokens(userModel, token);
        }else{
            throw new IncorrectPasswordException("Incorrect password entered");
        }
    }
}
