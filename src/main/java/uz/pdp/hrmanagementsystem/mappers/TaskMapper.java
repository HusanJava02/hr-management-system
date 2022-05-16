package uz.pdp.hrmanagementsystem.mappers;

import org.mapstruct.Mapper;
import uz.pdp.hrmanagementsystem.dto.TaskModel;
import uz.pdp.hrmanagementsystem.entities.Task;

@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface TaskMapper extends GenericMapper<Task, TaskModel> {
}
