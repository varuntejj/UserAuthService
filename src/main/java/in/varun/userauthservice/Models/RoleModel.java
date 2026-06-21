package in.varun.userauthservice.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Setter
@Getter
public class RoleModel extends BaseModel{
    private String name; //Mentor, Instructor, Admin, TA
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<UserModel> users;

    /*
    allowed permissions for this role
     */

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;


}
