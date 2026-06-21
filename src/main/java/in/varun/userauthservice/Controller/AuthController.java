package in.varun.userauthservice.Controller;

import in.varun.userauthservice.Dtos.LoginRequestDTO;
import in.varun.userauthservice.Dtos.SignupRequestDTO;
import in.varun.userauthservice.Dtos.UserDTO;
import in.varun.userauthservice.Models.UserModel;
import in.varun.userauthservice.Pojos.UserTokens;
import in.varun.userauthservice.Service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @PostMapping("/signup")
    ResponseEntity<UserDTO> signup(@RequestBody SignupRequestDTO signupRequestDTO) {

        try {
            UserModel user = authService.signup(signupRequestDTO.getName(),
                    signupRequestDTO.getEmail(),
                    signupRequestDTO.getPassword());

            UserDTO userDTO = user.convertToUserDTO();

            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);

        }catch (Exception e){
            return null;
        }

    }

    @PostMapping("/login")
    ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            UserTokens userTokens = authService.login(loginRequestDTO.getEmail(),
                    loginRequestDTO.getPassword());

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            /*
            cookie, token
            cookie, session id
            cookie, something else...
             */
            headers.add(HttpHeaders.COOKIE, userTokens.getToken());

            HttpHeaders httpHeaders = new HttpHeaders(headers);

            return new ResponseEntity<>(userTokens.getUser().convertToUserDTO(),
                    httpHeaders,
                    HttpStatus.OK);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            //return null;
        }
    }

}
