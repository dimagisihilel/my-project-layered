package lk.ijse.pawnshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class InventoryDto {
    private String inventoryId;
    private String customerId;
    private String itemType;
    private String caratValue;
    private double pricePerGram;
    private double weight;
    private String description;
    private double assessedValue;


    private CustomerDto customerDto;  // Add this field

    // Existing constructors and other methods...

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

}