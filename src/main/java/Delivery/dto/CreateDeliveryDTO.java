package Delivery.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateDeliveryDTO {
	
    private String receiverName;
    private String address;
    private UUID driverId;
}

