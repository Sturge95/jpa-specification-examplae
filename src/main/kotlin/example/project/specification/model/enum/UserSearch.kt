package example.project.specification.model.enum

import example.project.specification.model.Address
import example.project.specification.model.User
import kotlin.reflect.KProperty1


enum class UserSearchField(val fieldOne: KProperty1<User, *>, val fieldTwo: KProperty1<*, *>? = null) {
    AGE(User::age),
    DATE_OF_BIRTH(User::dateOfBirth),
    FIRST_NAME(User::firstName),
    LAST_NAME(User::lastName),
    ADDRESS_STREET(User::address, Address::street),
    ADDRESS_ID(User::address, Address::id),
    ADDRESS_POSTCODE(User::address, Address::postcode),
    PHONE_NUMBER(User::phoneNumber)
}