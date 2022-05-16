package uz.pdp.hrmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.hrmanagementsystem.entities.Company;
import uz.pdp.hrmanagementsystem.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Boolean existsByEmailAndEmailUUID(String email,String verification);

    User findUserByEmail(String email);




}
