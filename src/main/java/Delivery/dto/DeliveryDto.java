package Delivery.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import Delivery.Delivery;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryDto {
	private UUID id;
    private String deliveryNo;
    private String receiverName;
    private String address;
    private String driverName;
    private String status;
    private LocalDateTime deliveryTime;

    public static DeliveryDto fromEntity(Delivery d) {
        return DeliveryDto.builder()
        		.id(d.getId())
                .deliveryNo(d.getDeliveryNo())
                .receiverName(d.getReceiverName())
                .address(d.getAddress())
                .driverName(d.getDriver().getUserName())
                .status(d.getStatus().name())
                .deliveryTime(d.getDeliveryTime())
                .build();
    }
}
