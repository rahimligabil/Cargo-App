package Delivery.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import Delivery.Delivery;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryDetailDto {
	private UUID id; 
    private String DeliveryNo;
    private String receiverName;
    private String address;
    private String driverName;
    private String status;
    private String note;
    private String photoUrl;
    private LocalDateTime deliveryTime;

    public static DeliveryDetailDto fromEntity(Delivery d) {
        return DeliveryDetailDto.builder()
        		.id(d.getId())
                .DeliveryNo(d.getDeliveryNo())
                .receiverName(d.getReceiverName())
                .address(d.getAddress())
                .driverName(d.getDriver().getUserName())
                .status(d.getStatus().name())
                .note(d.getNote())
                .photoUrl(d.getDocumentUrl())
                .deliveryTime(d.getDeliveryTime())
                .build();
    }
}
