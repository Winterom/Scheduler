package alexey.grizly.com.properties.dtos.security.responses;

import alexey.grizly.com.properties.models.EMAIL_TYPE;
import alexey.grizly.com.properties.models.EmailProperty;
import lombok.Data;


@Data
public class EmailPropertiesTableResponseDto {
    private Long id;
    private EMAIL_TYPE type;
    private Boolean isEnabled;
    private String description;
    private String email;

    public EmailPropertiesTableResponseDto(EmailProperty emailProperty) {
        this.id = emailProperty.getId();
        this.type = emailProperty.getType();
        this.description = emailProperty.getDescription();
        this.email = emailProperty.getEmail();
    }
}
