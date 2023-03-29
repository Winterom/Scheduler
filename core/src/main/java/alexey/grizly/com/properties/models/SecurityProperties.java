package alexey.grizly.com.properties.models;

import alexey.grizly.com.properties.services.PropertiesService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Component
public class SecurityProperties {
    private JwtProperties jwtProperties;
    private UserPasswordStrange userPasswordStrange;
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
        private Integer passwordMinLowerCase;/*Минимальное количество прописных символов*/
        private Integer passwordMinNumber;/*Минимальное количество цифр*/
        private Integer passwordMinSymbol;/*Минимальное количество спец символов*/
        private Integer passwordMinUpperCase;/*Минимальное количество заглавных символов*/
        private Integer passwordMinCharacters;/*Минимальная длина пароля*/

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

}
