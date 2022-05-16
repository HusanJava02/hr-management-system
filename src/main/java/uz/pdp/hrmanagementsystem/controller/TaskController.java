package uz.pdp.hrmanagementsystem.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagementsystem.dto.TaskModel;
import uz.pdp.hrmanagementsystem.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Data
@RequestMapping(value = "/api/task")
public class TaskController {

    private TaskService taskService;

    @PostMapping
    public TaskModel addTask(@Valid @RequestBody TaskModel taskModel) {
        return taskService.create(taskModel);
    }


    @GetMapping
    public List<TaskModel> getAll(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "50") Integer size) {
        return taskService.findALlPageable(size,page);
    }

}
