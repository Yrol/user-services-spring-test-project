package blog.yrol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String firstName;
    String lastName;
    String email;
    String password;
    String reTypePassword;
}
