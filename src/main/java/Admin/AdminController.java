package Admin;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Delivery.Delivery;
import Delivery.DeliveryService;
import Delivery.dto.CreateDeliveryDTO;
import Delivery.dto.DeliveryDetailDto;
import Driver.DriverResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final DeliveryService deliveryService;
	private final AdminService adminService;
	
	@GetMapping("/deliveries/{deliveryId}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeliveryDetailDto> getDeliveryDetail(@PathVariable UUID deliveryId) 
    {
        DeliveryDetailDto dto = deliveryService.getDeliveryDetail(deliveryId);
        return ResponseEntity.ok(dto);
    }
	
	@GetMapping("/drivers")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DriverResponseDTO>> getallDrivers() 
    {
		List <DriverResponseDTO> dto = adminService.getAllDrivers();
        return ResponseEntity.ok(dto);
    }
	
	@PostMapping("/create/delivery")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createDelivery(@PathVariable CreateDeliveryDTO dto){
		Delivery delivery = deliveryService.createDelivery(dto);
		
		DeliveryDetailDto body = DeliveryDetailDto.builder()
	            .id(delivery.getId())
	            .DeliveryNo(delivery.getDeliveryNo())
	            .address(delivery.getAddress())
	            .receiverName(delivery.getReceiverName())
	            .driverName(delivery.getDriver().getUserName() + " " + delivery.getDriver().getUserSurname())
	            .status(delivery.getStatus().name())
	            .note(delivery.getNote())
	            .photoUrl(delivery.getDocumentUrl())
	            .deliveryTime(delivery.getDeliveryTime())
	            .build();
		
		
		return ResponseEntity.ok(body);
		
	}
}
