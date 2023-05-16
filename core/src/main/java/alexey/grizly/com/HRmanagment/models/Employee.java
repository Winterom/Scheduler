package alexey.grizly.com.HRmanagment.models;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class Employee {
    private Long id;
    private Long userId;
    private String name;
    private String surname;
    @Nullable
    private String lastname;
}
