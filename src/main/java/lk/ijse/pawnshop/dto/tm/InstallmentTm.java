package lk.ijse.pawnshop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class InstallmentTm {
    private String installmentId;
    private String dueDate;
    private double monthlyInstallment;
}
