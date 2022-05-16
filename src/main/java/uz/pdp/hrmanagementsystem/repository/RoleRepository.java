package uz.pdp.hrmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hrmanagementsystem.entities.Roles;
import uz.pdp.hrmanagementsystem.entities.User;
import uz.pdp.hrmanagementsystem.entities.enums.RoleName;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Boolean existsByRoleName(RoleName roleName);

    Roles findByRoleName(RoleName roleName);
}
