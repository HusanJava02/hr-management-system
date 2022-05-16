package uz.pdp.hrmanagementsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.hrmanagementsystem.dto.RoleModel;
import uz.pdp.hrmanagementsystem.entities.Roles;

@Mapper(componentModel = "spring")
public interface RoleMapper extends GenericMapper<Roles, RoleModel> {

}
