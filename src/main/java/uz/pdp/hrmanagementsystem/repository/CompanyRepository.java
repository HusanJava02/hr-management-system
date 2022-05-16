package uz.pdp.hrmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.hrmanagementsystem.entities.Company;


public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);


}
