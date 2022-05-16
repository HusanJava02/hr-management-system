package uz.pdp.hrmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementsystem.dto.CompanyModel;
import uz.pdp.hrmanagementsystem.dto.GenericResponse;
import uz.pdp.hrmanagementsystem.entities.Company;
import uz.pdp.hrmanagementsystem.entities.User;
import uz.pdp.hrmanagementsystem.exceptions.ResourceNotFoundException;
import uz.pdp.hrmanagementsystem.exceptions.UserAlreadyExistException;
import uz.pdp.hrmanagementsystem.mappers.CompanyMapper;
import uz.pdp.hrmanagementsystem.mappers.UserMapper;
import uz.pdp.hrmanagementsystem.repository.CompanyRepository;
import uz.pdp.hrmanagementsystem.repository.UserRepository;

@Service
public class CompanyService extends GenericService<Company, Long, CompanyModel, CompanyMapper, CompanyRepository> {


    private final UserRepository userRepository;

    @Autowired
    public CompanyService(CompanyRepository repository, CompanyMapper companyMapper, UserRepository userRepository, UserMapper userMapper) {
        super(repository, companyMapper, Company.class);
        this.userRepository = userRepository;
    }

    public CompanyModel createUserCompany(CompanyModel model, String email) {
        User user = userRepository.findUserByEmail(email);
        boolean existsByEmailOrPhoneNumber = repository.existsByEmailOrPhoneNumber(model.getEmail(), model.getPhoneNumber());
        if (existsByEmailOrPhoneNumber) {
            throw new UserAlreadyExistException(String.format("Company email , or password already exists in our DB, email: %s phone_number: %s", model.getEmail(), model.getPhoneNumber()));
        } else if (user == null) {
            throw new ResourceNotFoundException(String.format("User not found with given email: %s", email));
        }
        Company company = user.getCompany();
        company.setCompanyName(model.getCompanyName());
        company.setEmail(model.getEmail());
        company.setPhoneNumber(model.getPhoneNumber());
        Company savedCompany = repository.save(company);
        return mapper.toModel(savedCompany);

    }

    @Override
    public CompanyModel create(CompanyModel model) {
        return null;
    }

    @Override
    public CompanyModel update(CompanyModel model) {
        Company company = mapper.toEntity(model);
        return mapper.toModel(repository.save(company));
    }

    @Override
    public GenericResponse delete(Long id) {
        try{
            repository.deleteById(id);
            return GenericResponse.builder().message("Deleted your Account and all your company").success(true).build();
        }catch (Exception e){
            return GenericResponse.builder().message("cannot delete").success(false).build();
        }
    }
}
