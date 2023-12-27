package lk.ijse.pawnshop.dto.tm;

import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;
import javafx.scene.control.Button;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
public class CustomerTm {
    private TextField id;
    private TextField name;
    private TextField contactNo;
    private TextField NIC;
    private TextField address;
    private TextField email;
    private Button action1;
    private Button action2;

    public CustomerTm(){
        id = new TextField();
        name = new TextField();
        contactNo = new TextField();
        NIC = new TextField();
        address = new TextField();
        email = new TextField();
    }

}
