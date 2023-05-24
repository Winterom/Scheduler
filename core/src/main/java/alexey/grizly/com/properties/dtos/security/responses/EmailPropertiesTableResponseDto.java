package alexey.grizly.com.properties.dtos.security.responses;

import alexey.grizly.com.properties.models.EmailPropertyModel;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@Data
public class EmailPropertiesTableResponseDto {
    private Long id;
    private String type;
    private Boolean isEnabled;
    private String description;
    private String email;

    public EmailPropertiesTableResponseDto(@NotNull EmailPropertyModel emailPropertyModel) {
        this.id = emailPropertyModel.getId();
        this.type = emailPropertyModel.getType().getTitle();
        this.isEnabled = emailPropertyModel.getIsEnabled();
        this.description = emailPropertyModel.getDescription();
        this.email = emailPropertyModel.getEmail();
    }
}
