package lk.ijse.pawnshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class PaymentDetailsDto {
    private String paymentId;
    private String inventoryId;
    private LocalDate dateGranted;
    private LocalDate dueDate;

    private InventoryDto inventoryDto;
    private List<InstallmentDto> installmentDtoList;

    private String customer_id;
    private String name;


}
