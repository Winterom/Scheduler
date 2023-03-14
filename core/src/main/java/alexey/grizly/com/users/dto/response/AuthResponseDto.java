package alexey.grizly.com.users.dto.response;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String access_token;
    private String refresh_token;
    private String session;

    public static Builder builder(){
        return new Builder();
    }

    public AuthResponseDto(){}
    public static class Builder{
        private final AuthResponseDto dto;
        public Builder(){
            this.dto = new AuthResponseDto();
        }
        public Builder setAccessToken(String accessToken){
            dto.setAccess_token(accessToken);
            return this;
        }
                public Builder setRefreshToken(String refreshToken){
            dto.setRefresh_token(refreshToken);
            return this;
        }

        public Builder setSession(String session){
            dto.setSession(session);
            return this;
        }


        public AuthResponseDto build(){
            return this.dto;
        }
    }
}
