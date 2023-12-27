package lk.ijse.pawnshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data @AllArgsConstructor @NoArgsConstructor
public class InstallmentDto {
    private String installmentId;
    private String paymentId;
    private double amount;
    private String paymentStatus;

    private LocalDate dateGranted;
    private LocalDate dueDate;

}
