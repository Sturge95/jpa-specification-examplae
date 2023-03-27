package example.project.specification.validator.factory

import example.project.specification.model.enum.UserSearchField
import example.project.specification.validator.DateFieldValidator
import example.project.specification.validator.IntegerFieldValidator
import example.project.specification.validator.StringFieldValidator
import example.project.specification.validator.UserSearchFieldValidator

class UserSearchFieldValidatorFactory(userSearchField: UserSearchField) {

    val validator: UserSearchFieldValidator

    init {
        validator = when (userSearchField) {
            UserSearchField.ADDRESS_ID,
            UserSearchField.AGE -> IntegerFieldValidator(userSearchField)

            UserSearchField.DATE_OF_BIRTH -> DateFieldValidator(userSearchField)
            UserSearchField.FIRST_NAME,
            UserSearchField.LAST_NAME,
            UserSearchField.ADDRESS_STREET,
            UserSearchField.ADDRESS_POSTCODE,
            UserSearchField.PHONE_NUMBER -> StringFieldValidator(userSearchField)
        }
    }


}