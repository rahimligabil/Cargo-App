package Delivery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabil.kargo.user.User;
import com.gabil.kargo.user.UserRepository;

import Delivery.dto.CreateDeliveryDTO;
import Delivery.dto.DeliveryDetailDto;
import Delivery.dto.DeliveryDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {
	
	private final DeliveryRepository deliveryRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Delivery createDelivery(CreateDeliveryDTO dto) {
		 Delivery delivery = Delivery.builder()
			        .id(UUID.randomUUID())
			        .deliveryNo(generateDeliveryNo())
			        .receiverName(dto.getReceiverName())
			        .address(dto.getAddress())
			        .status(DeliveryStatus.BEKLEMEDE)
			        .build();

		 User driver = userRepository.findById(dto.getDriverId())
			        .orElseThrow(() -> new EntityNotFoundException("Sürücü bulunamadı"));

			    delivery.setDriver(driver);

			    
			    deliveryRepository.save(delivery);

			    log.info("Yeni teslimat oluşturuldu: {} | Sürücü: {}", 
			             delivery.getDeliveryNo(), driver.getUserName() + " " + driver.getUserSurname());

			    return delivery;
	}
	
	
	@Transactional(readOnly = true)
	public List<DeliveryDto> getAllDeliveries(){
		return null;
		
	}
	
	@Transactional(readOnly = true)
	public DeliveryDetailDto getDeliveryDetail(UUID id) {
	
		Delivery delivery = deliveryRepository.findById(id).orElseThrow();
		
		DeliveryDetailDto dto = DeliveryDetailDto.builder()
				.id(id)
				.DeliveryNo(delivery.getDeliveryNo())
				.address(delivery.getAddress())
				.receiverName(delivery.getReceiverName())
				.driverName(delivery.getDriver().getUserName() + " "+ delivery.getDriver().getUserSurname())
				.status(delivery.getStatus().name())
				.note(delivery.getNote())
				.photoUrl(delivery.getDocumentUrl())
				.deliveryTime(delivery.getDeliveryTime()).build();
		
		return dto;
	}
	
	

	private String generateDeliveryNo() {
	    long count = deliveryRepository.count() + 1; 
	    LocalDateTime now = LocalDateTime.now();

	    return String.format(
	        "OZYL-%d-%02d-%06d",
	        now.getYear(),         
	        now.getMonthValue(),  
	        count                  
	    );
	}

}
