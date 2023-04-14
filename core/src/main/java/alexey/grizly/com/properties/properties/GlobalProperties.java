package alexey.grizly.com.properties.properties;

import alexey.grizly.com.properties.services.PropertiesService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.TimeZone;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class GlobalProperties {
    private String host;
    private TimeZone timeZone;
    @JsonIgnore
    private PropertiesService propertiesService;
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

    public GlobalProperties() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    @PostConstruct
    public void init(){
       updateProperty((GlobalProperties) propertiesService.getProperty(GlobalProperties.class));
    }


    public void updateProperty(GlobalProperties newProperty){
        try {
            this.writeLock.lock();
            this.host = newProperty.getHost();
            this.timeZone = newProperty.getTimeZone();
        }finally {
            this.writeLock.unlock();
        }
    }

    public String getHost() {
        try {
            this.readLock.lock();
            return this.host;
        }finally {
            this.readLock.unlock();
        }
    }

    public TimeZone getTimeZone() {
        try {
            this.readLock.lock();
            return this.timeZone;
        }finally {
            this.readLock.unlock();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("host", host)
                .append("timeZone", timeZone)
                .toString();
    }
}
