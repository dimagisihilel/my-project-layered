package lk.ijse.pawnshop.dto.tm;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data @AllArgsConstructor
public class EmployeeTm {
    private TextField id;
    private TextField name;
    private TextField address;
    private TextField position;
    private TextField salary;
    private Button action1;
    private Button action2;

    public EmployeeTm(){
        id = new TextField();
        name = new TextField();
        address = new TextField();
        position = new TextField();
        salary = new TextField();

    }



}
