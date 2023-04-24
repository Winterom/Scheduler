package alexey.grizly.com.properties.dtos.security.responses;

import alexey.grizly.com.properties.models.EmailProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@Data
public class EmailPropertiesTableResponseDto {
    private Long id;
    private String type;
    private Boolean isEnabled;
    private String description;
    private String email;

    public EmailPropertiesTableResponseDto(@NotNull EmailProperty emailProperty) {
        this.id = emailProperty.getId();
        this.type = emailProperty.getType().getTitle();
        this.isEnabled = emailProperty.getIsEnabled();
        this.description = emailProperty.getDescription();
        this.email = emailProperty.getEmail();
    }
}
