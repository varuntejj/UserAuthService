package in.varun.userauthservice.Dtos;

import in.varun.userauthservice.Models.RoleModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<RoleModel> roles;
}
