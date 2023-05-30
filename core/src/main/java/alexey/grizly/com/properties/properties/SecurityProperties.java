package alexey.grizly.com.properties.properties;

import alexey.grizly.com.properties.services.PropertiesService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Component
public class SecurityProperties {
    private JwtProperties jwtProperties;
    private UserPasswordStrange userPasswordStrange;
    private RestorePasswordTokenProperty restorePasswordTokenProperty;
    private ApprovedEmailProperty approvedEmailProperty;
    private PasswordProperty passwordProperty;
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
        this.passwordProperty = new PasswordProperty();
        this.approvedEmailProperty = new ApprovedEmailProperty();
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
            this.passwordProperty = newProperty.getPasswordProperty();
            this.approvedEmailProperty = newProperty.getApprovedEmailProperty();
        }finally {
            this.writeLock.unlock();
        }

    }
    public void updateUserPasswordStrange(UserPasswordStrange property){
        try {
            this.writeLock.lock();
            this.userPasswordStrange = property;
        }finally {
            this.writeLock.unlock();
        }
    }

    public void updateRestorePasswordTokenProperty(RestorePasswordTokenProperty property){
        try {
            this.writeLock.lock();
            this.restorePasswordTokenProperty = property;
        }finally {
            this.writeLock.unlock();
        }
    }
    public void updateJwtProperties(JwtProperties property){
        try {
            this.writeLock.lock();
            this.jwtProperties = property;
        }finally {
            this.writeLock.unlock();
        }
    }
    public void updatePasswordProperty(PasswordProperty property){
        try {
            this.writeLock.lock();
            this.passwordProperty = property;
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
    public PasswordProperty getPasswordProperty() {
        try{
            this.readLock.lock();
            return this.passwordProperty;
        }finally {
            this.readLock.unlock();
        }
    }
    public ApprovedEmailProperty getApprovedEmailProperty() {
        try{
            this.readLock.lock();
            return this.approvedEmailProperty;
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
            return new ToStringBuilder(this)
                    .append("secret", secret)
                    .append("jwtLifetime", jwtLifetime)
                    .append("jwtRefreshLifetime", jwtRefreshLifetime)
                    .append("emailVerifyTokenLifeTime", emailVerifyTokenLifeTime)
                    .toString();
        }
    }

    /*Если значение установлено в 0, то при валидации не используется*/
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
        private Integer restorePasswordTokenLength=40;
        private Long pauseBetweenNextTokenGenerate = 600000L;//минимально 1 минута
        private Long restorePasswordTokenLifetime=24L;
        private ChronoUnit unit=ChronoUnit.HOURS;

        public void setRestorePasswordTokenLength(Integer restorePasswordTokenLength) {
            if(restorePasswordTokenLength>40){
                this.restorePasswordTokenLength=40;
                return;
            }
            this.restorePasswordTokenLength = restorePasswordTokenLength;
        }
    }
    @Getter
    @Setter
    public static class ApprovedEmailProperty {
        private Integer approvedEmailTokenLength=40;
        private Long pauseBetweenNextTokenGenerate = 600000L;//минимально 1 минута
        private Long approvedEmailTokenLifetime = 24L;
        private ChronoUnit unit=ChronoUnit.HOURS;

        public void setApprovedEmailTokenLength(Integer restorePasswordTokenLength) {
            if(restorePasswordTokenLength>40){
                this.approvedEmailTokenLength=40;
                return;
            }
            this.approvedEmailTokenLength = restorePasswordTokenLength;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("approvedEmailTokenLength", approvedEmailTokenLength)
                    .append("approvedEmailTokenLifetime", approvedEmailTokenLifetime)
                    .append("unit", unit)
                    .toString();
        }
    }
    @Getter
    @Setter
    public static class PasswordProperty {
        private Long passwordExpired = 6L;
        private ChronoUnit unit = ChronoUnit.MONTHS;

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("passwordExpired", passwordExpired)
                    .append("unit", unit)
                    .toString();
        }
    }
}
