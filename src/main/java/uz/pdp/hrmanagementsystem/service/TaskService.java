package uz.pdp.hrmanagementsystem.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementsystem.dto.GenericResponse;
import uz.pdp.hrmanagementsystem.dto.TaskModel;
import uz.pdp.hrmanagementsystem.entities.Task;
import uz.pdp.hrmanagementsystem.mappers.TaskMapper;
import uz.pdp.hrmanagementsystem.repository.TaskRepository;
import uz.pdp.hrmanagementsystem.service.GenericService;

import javax.validation.constraints.NotNull;

@Service
public class TaskService extends GenericService<Task, Long, TaskModel, TaskMapper,TaskRepository> {

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper, Task.class);
    }

    @Override
    public TaskModel create(TaskModel model) {
        Task task = mapper.toEntity(model);
        Task saved = repository.save(task);
        return mapper.toModel(saved);
    }

    @Override
    public TaskModel update(TaskModel model) {
        return null;
    }

    @Override
    public GenericResponse delete(Long id) {
        return null;
    }
}
