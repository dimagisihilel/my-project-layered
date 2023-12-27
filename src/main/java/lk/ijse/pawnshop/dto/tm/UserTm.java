package lk.ijse.pawnshop.dto.tm;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserTm {
    private TextField id;
    private TextField username;
    private TextField password;
    private Button action1;
    private Button action2;

    public UserTm(){
        id = new TextField();
        username = new TextField();
        password = new TextField();
    }
}
