package alexey.grizly.com.users.extractors;

import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.models.EUserStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class UserAccountWithRolesExtractor implements ResultSetExtractor<UserProfileResponse> {

    @Override
    public UserProfileResponse extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(!rs.next()){
            return null;
        }
        UserProfileResponse userAccount = new UserProfileResponse();
        userAccount.setId(rs.getLong("id"));
        userAccount.setEmail(rs.getString("email"));
        userAccount.setIsEmailVerified(rs.getBoolean("is_email_verified"));
        userAccount.setStatus(EUserStatus.valueOf(rs.getString("e_status")));
        userAccount.setPhone(rs.getString("phone"));
        userAccount.setIsPhoneVerified(rs.getBoolean("is_phone_verified"));
        userAccount.setCredentialExpiredTime(rs.getTimestamp("credential_expired").toLocalDateTime());
        userAccount.setCreatedAt(rs.getTimestamp("createdat").toLocalDateTime());
        userAccount.setUpdatedAt(rs.getTimestamp("updatedat").toLocalDateTime());
        userAccount.setRoles(new HashSet<>());
        UserProfileResponse.Role role = new UserProfileResponse.Role();
        role.setTitle(rs.getString("title"));
        role.setDescription(rs.getString("description"));
        userAccount.getRoles().add(role);
        while (rs.next()){
            UserProfileResponse.Role role1 = new UserProfileResponse.Role();
            role1.setTitle(rs.getString("title"));
            role1.setDescription(rs.getString("description"));
            userAccount.getRoles().add(role1);
        }
        return userAccount;
    }
}
