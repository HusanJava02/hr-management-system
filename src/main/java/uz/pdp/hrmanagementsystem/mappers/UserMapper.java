package uz.pdp.hrmanagementsystem.mappers;

import org.mapstruct.Mapper;
import uz.pdp.hrmanagementsystem.dto.UserModel;
import uz.pdp.hrmanagementsystem.entities.User;

@Mapper(componentModel = "spring",uses = {RoleMapper.class,CompanyMapper.class})
public interface UserMapper extends GenericMapper<User,UserModel>{

}
