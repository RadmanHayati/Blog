package com.scenius.mosaheb.repositories;

import com.scenius.mosaheb.entities.Role;
import com.scenius.mosaheb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String mobile);
    User findByRole(Role role);
}
