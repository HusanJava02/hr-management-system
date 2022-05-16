package uz.pdp.hrmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hrmanagementsystem.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
