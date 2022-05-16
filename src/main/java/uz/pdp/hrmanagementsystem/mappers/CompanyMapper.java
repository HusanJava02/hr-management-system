package uz.pdp.hrmanagementsystem.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.pdp.hrmanagementsystem.dto.CompanyModel;
import uz.pdp.hrmanagementsystem.entities.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends GenericMapper<Company, CompanyModel> {

}
