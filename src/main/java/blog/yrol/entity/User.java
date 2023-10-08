package blog.yrol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String id;
    String firstName;
    String lastName;
    String email;
    String password;
    String reTypePassword;
}
