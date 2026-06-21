package in.varun.userauthservice.Models;

import in.varun.userauthservice.Dtos.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Entity
@Getter
@Setter
public class UserModel extends BaseModel{
    private String email;
    private String password;
    private String name;


    @ManyToMany
    @JoinTable(
            name = "user_model_roles",
            joinColumns = @JoinColumn(name = "user_model_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleModel> roles;

    public UserDTO convertToUserDTO() {
        UserDTO userDto = new UserDTO();
        userDto.setId(getId());
        userDto.setEmail(this.email);
        userDto.setName(this.name);
        userDto.setRoles(this.roles);
        return userDto;
    }
}
