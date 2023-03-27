package example.project.specification.service

import example.project.specification.model.User
import example.project.specification.model.dto.Response
import example.project.specification.model.enum.UserSearchField

interface UserService {
    fun getAllByAgeGreaterThan(age: Int): List<User>

    fun countUsersWithAgeGreaterThanOrEqualTo(age: Int): Long

    fun getAllUsersOnStreet(street: String): List<User>

    fun preformUserSearch(searchFields: Map<UserSearchField, String>): List<User>

    fun userSearch(rawSearchFields: Map<String, String>): Response<List<User>>

    fun userSearchAllFieldsMustBeValid(rawSearchFields: Map<String, String>): Response<List<User>>

}