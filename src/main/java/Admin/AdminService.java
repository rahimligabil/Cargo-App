package Admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabil.kargo.user.User;
import com.gabil.kargo.user.UserRepository;

import Driver.DriverResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;

    public List<DriverResponseDTO> getAllDrivers() {
        List<User> drivers = userRepository.findAllDrivers();

        return drivers.stream()
                .map(user -> DriverResponseDTO.builder()
                        .id(user.getUserId())
                        .firstName(user.getUserName())
                        .lastName(user.getUserSurname())
                        .email(user.getUserEmail())
                        .phone(user.getPhone())
                        .status(user.isActive() ? "AKTIF" : "PASIF")
                        .build()
                )
                .toList();
    }
}
