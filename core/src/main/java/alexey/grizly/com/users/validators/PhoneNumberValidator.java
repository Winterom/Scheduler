package alexey.grizly.com.users.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private boolean required = true;
    @Override
    public void initialize(PhoneNumber phoneNumber) {
        ConstraintValidator.super.initialize(phoneNumber);
        this.required= phoneNumber.required();
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext validatorContext) {
        if(!required&&((contactField==null)||(contactField.isEmpty()))){
            return true;
        }
        return contactField != null
                &&contactField.length()==12
                &&contactField.matches("^\\+\\d{11}");
    }
}
