package alexey.grizly.com.users.models.roles;

import alexey.grizly.com.users.models.ERoleStatus;
import lombok.Data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
@Data
public class CheckedRoleForDelete {
   private Integer catalogCount;/*Количество каталогов проверено*/
   private Integer roleCount;/*Количество ролей проверено*/
   private Collection<CheckedRole> roleAssignedUsers;


   @Data
   public static class CheckedRole{
      private Long key;
      private String label;
      private Boolean isCatalog;
      private Long parentId;
      private ERoleStatus status;
      private String path;
      private List<User> users = new LinkedList<>();
   }

   @Data
   public static class User{
      private Long key;
      private String email;
   }
}
