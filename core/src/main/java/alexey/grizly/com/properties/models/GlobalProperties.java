package alexey.grizly.com.properties.models;

import alexey.grizly.com.properties.services.PropertiesService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.TimeZone;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Setter
@Component
public class GlobalProperties {
    private String host;
    private TimeZone timeZone;
    @JsonIgnore
    private final PropertiesService propertiesService;
    @JsonIgnore
    private final Lock writeLock;
    @JsonIgnore
    private final Lock readLock;
    @Autowired
    public GlobalProperties(PropertiesService propertiesService) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
        this.propertiesService = propertiesService;
    }

    @PostConstruct
    public void init(){
        propertiesService.getProperty(GlobalProperties.class);
    }


    public void updateProperty(GlobalProperties newProperty){
        try {
            writeLock.lock();
            this.host = newProperty.getHost();
            this.timeZone = newProperty.getTimeZone();
        }finally {
            writeLock.unlock();
        }
    }

    public String getHost() {

        return host;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }
}
