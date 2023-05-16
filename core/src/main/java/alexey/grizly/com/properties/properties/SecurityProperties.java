package alexey.grizly.com.properties.properties;

import alexey.grizly.com.properties.services.PropertiesService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Component
public class SecurityProperties {
    private JwtProperties jwtProperties;
    private UserPasswordStrange userPasswordStrange;
    private RestorePasswordTokenProperty restorePasswordTokenProperty;
    @JsonIgnore
    private PropertiesService propertiesService;
    @JsonIgnore
    private final Lock writeLock;
    @JsonIgnore
    private final Lock readLock;
    public SecurityProperties() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
        this.jwtProperties = new JwtProperties();
        this.userPasswordStrange = new UserPasswordStrange();
    }

    @Autowired
    public SecurityProperties(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    @PostConstruct
    public void init(){
        updateProperty((SecurityProperties) propertiesService.getProperty(SecurityProperties.class));
    }

    public void updateProperty(SecurityProperties newProperty){
        try {
            this.writeLock.lock();
            this.jwtProperties = newProperty.jwtProperties;
            this.userPasswordStrange = newProperty.getUserPasswordStrange();
            this.restorePasswordTokenProperty = newProperty.getRestorePasswordTokenProperty();
        }finally {
            this.writeLock.unlock();
        }

    }
    public void updateUserPasswordStrange(UserPasswordStrange properties){
        try {
            this.writeLock.lock();
            this.userPasswordStrange = properties;
        }finally {
            this.writeLock.unlock();
        }
    }

    public void updateRestorePasswordTokenProperty(RestorePasswordTokenProperty properties){
        try {
            this.writeLock.lock();
            this.restorePasswordTokenProperty = properties;
        }finally {
            this.writeLock.unlock();
        }
    }
    public void updateJwtProperties(JwtProperties properties){
        try {
            this.writeLock.lock();
            this.jwtProperties = properties;
        }finally {
            this.writeLock.unlock();
        }
    }
    public JwtProperties getJwtProperties() {
        try {
            this.readLock.lock();
            return this.jwtProperties;
        }finally {
            this.readLock.unlock();
        }
    }

    public UserPasswordStrange getUserPasswordStrange() {
        try {
            this.readLock.lock();
            return this.userPasswordStrange;
        }finally {
            this.readLock.unlock();
        }
    }

    public RestorePasswordTokenProperty getRestorePasswordTokenProperty() {
        try{
            this.readLock.lock();
            return this.restorePasswordTokenProperty;
        }finally {
            this.readLock.unlock();
        }

    }

    @Getter
    @Setter
    public static class JwtProperties {
        private String secret;
        private Integer jwtLifetime;
        private Integer jwtRefreshLifetime;
        private Integer emailVerifyTokenLifeTime;

        @Override
        public String toString() {
            return "JwtProperties{" +
                    "secret='" + secret + '\'' +
                    ", jwtLifetime=" + jwtLifetime +
                    ", jwtRefreshLifetime=" + jwtRefreshLifetime +
                    ", emailVerifyTokenLifeTime=" + emailVerifyTokenLifeTime +
                    '}';
        }
    }

    /*Если значение установлено в 0 то при валидации не используется*/
    @Getter
    @Setter
    public static class UserPasswordStrange{
        private Integer passwordMinLowerCase=0;/*Минимальное количество прописных символов*/
        private Integer passwordMinNumber=0;/*Минимальное количество цифр*/
        private Integer passwordMinSymbol=0;/*Минимальное количество спец символов*/
        private Integer passwordMinUpperCase=0;/*Минимальное количество заглавных символов*/
        private Integer passwordMinCharacters=0;/*Минимальная длина пароля*/

        @Override
        public String toString() {
            return "UserPasswordStrange{" +
                    "passwordMinLowerCase=" + passwordMinLowerCase +
                    ", passwordMinNumber=" + passwordMinNumber +
                    ", passwordMinSymbol=" + passwordMinSymbol +
                    ", passwordMinUpperCase=" + passwordMinUpperCase +
                    ", passwordMinCharacters=" + passwordMinCharacters +
                    '}';
        }
    }
    @Getter
    @Setter
    public static class RestorePasswordTokenProperty {
        private Integer restorePasswordTokenLength;
        private Long restorePasswordTokenLifetime;
        private TemporalUnit unit;
    }

}
