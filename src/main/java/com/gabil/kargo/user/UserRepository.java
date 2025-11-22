package com.gabil.kargo.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{
	Optional<User> findByFirebaseUid(String uid);
    Optional<User> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
    List<User> findAllDrivers();
}
