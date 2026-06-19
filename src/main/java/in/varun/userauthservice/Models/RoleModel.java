package in.varun.userauthservice.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class RoleModel extends BaseModel{
    private String name; //Mentor, Instructor, Admin, TA
    /*
    allowed permissions for this role
     */

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;


}
