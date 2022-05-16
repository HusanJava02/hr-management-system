package uz.pdp.hrmanagementsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@AllArgsConstructor
@MappedSuperclass
@NoArgsConstructor
@Data
public class Base {

    @CreatedBy
    private String createdBy;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
