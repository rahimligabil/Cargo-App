package Delivery;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.gabil.kargo.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery",schema = "cargo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Delivery {

	@Id
	@Column(name = "Id")
	@EqualsAndHashCode.Include
	private UUID id;
	
	@Column(name = "delivery_no",nullable = false,unique = true)
	private String deliveryNo;
	
	@Column(name = "receiver_name",nullable = false,length = 256)
	private String receiverName;
	

	@Column(name = "address",nullable = false,length = 512)
	private String address;
	
	
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "driver_id")
	private User driver;
	
	@Column(name = "status",nullable = false,length = 50)
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	
	@Column(name = "delivery_time")
	private LocalDateTime deliveryTime;
	
	@Column(name = "created_at",updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@Column(name = "delivery_note",length = 256)
	private String note;
	

	@Column(name = "document_url")
	private String documentUrl;
	
	
	
	
	
}
