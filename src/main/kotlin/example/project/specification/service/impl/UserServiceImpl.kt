package example.project.specification.service.impl

import example.project.specification.model.User
import example.project.specification.repo.UserRepo
import example.project.specification.repo.specification.UserSpecification
import example.project.specification.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepo) : UserService {
    override fun searchByAgeGreaterThan(age: Int): List<User> {
        val ageSpecification = UserSpecification.ageGreaterThanOrEqualTo(age)
        return userRepo.findAll(ageSpecification)
    }

    override fun countUsersWithAgeGreaterThanOrEqualTo(age: Int): Long {
        val ageSpecification = UserSpecification.ageGreaterThanOrEqualTo(age)
        return userRepo.count(ageSpecification)
    }

}