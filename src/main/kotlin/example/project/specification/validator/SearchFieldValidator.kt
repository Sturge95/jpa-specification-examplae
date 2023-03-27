package example.project.specification.validator

import example.project.specification.model.enum.UserSearchField
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

interface SearchFieldValidator {

    fun validate(stringValue: String): Boolean

    fun getValidationError(): String

}

abstract class UserSearchFieldValidator(
    protected val userSearchField: UserSearchField
) : SearchFieldValidator

class DateFieldValidator(userSearchField: UserSearchField) : UserSearchFieldValidator(userSearchField) {
    override fun validate(stringValue: String): Boolean {
        val splitDates = stringValue.split(",")
        val noDatesPresent = splitDates.all { it.trim().isBlank() }
        if (noDatesPresent) {
            return false
        }
        if (splitDates.size != 2) {
            return false
        }
        splitDates.forEach {
            try {
                val searchString = it.trim();
                if (searchString.isNotBlank()) {
                    LocalDateTime.parse(it.trim())
                }
            } catch (exception: DateTimeParseException) {
                return false
            }

        }
        return true
    }

    override fun getValidationError(): String {
        return "$userSearchField must contain a valid to or from date (or both) seperated by a comma"
    }
}

class IntegerFieldValidator(userSearchField: UserSearchField) : UserSearchFieldValidator(userSearchField) {
    override fun validate(stringValue: String): Boolean {
        stringValue.split(",").forEach() {
            try {
                it.trim().toInt()
            } catch (exception: NumberFormatException) {
                return false
            }
        }

        return true;
    }

    override fun getValidationError(): String {
        return "$userSearchField must only contain integers"
    }
}


class StringFieldValidator(userSearchField: UserSearchField) : UserSearchFieldValidator(userSearchField) {
    override fun validate(stringValue: String): Boolean {
        return stringValue.isNotBlank()
    }

    override fun getValidationError(): String {
        return "$userSearchField must not be empty"
    }

}