package alexey.grizly.com.users.messages.roles.response;

import java.util.Collection;

public interface MessageTreeNode<T>{
    Long getParent();
    Long getKey();
    Collection<T> getChild();

}
