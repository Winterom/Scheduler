package alexey.grizly.com.properties.models;

public enum EEmailType {
    ADMIN_SENDER,REGISTER_SENDER,MANAGER_TYPE;
    public String getTitle() {
        String title = null;
        switch (this) {
            case ADMIN_SENDER -> title = "Административный";
            case REGISTER_SENDER -> title = "Регистратура";
            case MANAGER_TYPE -> title = "Управленческая";
        }
        return title;
    }
}
