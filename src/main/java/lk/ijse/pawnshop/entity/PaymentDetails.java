package lk.ijse.pawnshop.entity;

import lk.ijse.pawnshop.dto.InstallmentDto;
import lk.ijse.pawnshop.dto.InventoryDto;

import java.time.LocalDate;
import java.util.List;

public class PaymentDetails {
    private String paymentId;
    private String inventoryId;
    private LocalDate dateGranted;
    private LocalDate dueDate;

    private InventoryDto inventoryDto;
    private List<InstallmentDto> installmentDtoList;

    private String customer_id;
    private String name;

    public PaymentDetails() {
    }

    public PaymentDetails(String paymentId, String inventoryId, LocalDate dateGranted, LocalDate dueDate, InventoryDto inventoryDto, List<InstallmentDto> installmentDtoList, String customer_id, String name) {
        this.paymentId = paymentId;
        this.inventoryId = inventoryId;
        this.dateGranted = dateGranted;
        this.dueDate = dueDate;
        this.inventoryDto = inventoryDto;
        this.installmentDtoList = installmentDtoList;
        this.customer_id = customer_id;
        this.name = name;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public LocalDate getDateGranted() {
        return dateGranted;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public InventoryDto getInventoryDto() {
        return inventoryDto;
    }

    public List<InstallmentDto> getInstallmentDtoList() {
        return installmentDtoList;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getName() {
        return name;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setDateGranted(LocalDate dateGranted) {
        this.dateGranted = dateGranted;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setInventoryDto(InventoryDto inventoryDto) {
        this.inventoryDto = inventoryDto;
    }

    public void setInstallmentDtoList(List<InstallmentDto> installmentDtoList) {
        this.installmentDtoList = installmentDtoList;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
