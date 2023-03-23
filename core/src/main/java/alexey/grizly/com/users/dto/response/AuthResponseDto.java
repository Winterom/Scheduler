package alexey.grizly.com.users.dto.response;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String access_token;

    public AuthResponseDto(String access_token){
        this.access_token = access_token;
    }

}
