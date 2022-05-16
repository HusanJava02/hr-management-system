package uz.pdp.hrmanagementsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uz.pdp.hrmanagementsystem.entities.enums.InOutType;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmployeeInOut extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Timestamp timestamp;

    @Enumerated(value = EnumType.STRING)
    private InOutType inOutType;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
