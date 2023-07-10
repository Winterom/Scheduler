package alexey.grizly.com.commons.exceptions;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
@Slf4j
public class PSQLErrorsTranslator {
    private final Map<String,String> translator = new HashMap<>();

    @PostConstruct
    public void init(){
        this.translator.put("23505","Объект с такими уникальными данными существует");
    }


    public String getMessage(ServerErrorMessage errorMessage){
        String sqlState = errorMessage.getSQLState();
        String message = this.translator.get(sqlState);
        if(message==null){
            log.error("Не найдено описание для sqlState: "+sqlState+" errorMessage: "+errorMessage.getMessage());
            return errorMessage.getMessage();
        }
        return message;
    }
}
