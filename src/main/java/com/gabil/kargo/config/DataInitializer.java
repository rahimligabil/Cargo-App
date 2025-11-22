package com.gabil.kargo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gabil.kargo.role.Role;
import com.gabil.kargo.role.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final JdbcTemplate jdbcTemplate;

	@Value("${app.seed.roles:true}")
	private boolean seedRoles;

	@Override
	@Transactional
	public void run(String... args) {
		if (!seedRoles) {
			log.debug("Role seeding skipped because app.seed.roles=false");
			return;
		}

		jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS cargo");

		createRoleIfMissing("ROLE_DRIVER");
		createRoleIfMissing("ROLE_ADMIN");

		roleRepository.findAll()
				.forEach(role -> log.info("Available role {} -> {}", role.getRoleId(), role.getRoleName()));
	}

	private void createRoleIfMissing(String roleName) {
		roleRepository.findByRoleName(roleName)
				.orElseGet(() -> {
					log.info("Seeding missing role: {}", roleName);
					return roleRepository.save(Role.builder().roleName(roleName).build());
				});
	}
}
