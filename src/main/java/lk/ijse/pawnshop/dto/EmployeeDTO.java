package lk.ijse.pawnshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EmployeeDTO {
    private String id;
    private String name;
    private String address;
    private String position;
    private double salary;
}
