package lk.ijse.pawnshop.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CustomerDto {
    private String id;
    private String name;
    private String contactNo;
    private String NIC;
    private String address;
    private String email;

}
