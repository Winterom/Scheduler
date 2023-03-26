package alexey.grizly.com.users.dto.response;

import lombok.Data;

public record AuthResponseDto(String access_token, String expire) {

}
