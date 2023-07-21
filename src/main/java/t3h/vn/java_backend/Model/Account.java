package t3h.vn.java_backend.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {
    private int ID;
    private String email;
    private String avatar;
    private String pass;
    private String address;
    private int status;
}
