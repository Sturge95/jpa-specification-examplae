package example.project.specification.service

import example.project.specification.model.User

interface UserService {
    fun searchByAgeGreaterThan(age: Int): List<User>

    fun countUsersWithAgeGreaterThanOrEqualTo(age: Int): Long

}