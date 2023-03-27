package example.project.specification.service.impl

import example.project.specification.model.User
import example.project.specification.model.dto.Response
import example.project.specification.model.dto.ResponseCreation
import example.project.specification.model.enum.UserSearchField
import example.project.specification.repo.UserRepo
import example.project.specification.repo.specification.UserSpecification
import example.project.specification.service.UserService
import example.project.specification.validator.factory.UserSearchFieldValidatorFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepo) : UserService {
    override fun getAllByAgeGreaterThan(age: Int): List<User> {
        val ageSpecification = UserSpecification.ageGreaterThanOrEqualTo(age)
        return userRepo.findAll(ageSpecification)
    }

    override fun countUsersWithAgeGreaterThanOrEqualTo(age: Int): Long {
        val ageSpecification = UserSpecification.ageGreaterThanOrEqualTo(age)
        return userRepo.count(ageSpecification)
    }

    override fun getAllUsersOnStreet(street: String): List<User> {
        val streetSpecification = UserSpecification.usersLivingOnStreet(street)
        return userRepo.findAll(streetSpecification)
    }

    override fun preformUserSearch(searchFields: Map<UserSearchField, String>): List<User> {
        val userSearchSpecification = UserSpecification.andSearch(searchFields)
        return userRepo.findAll(userSearchSpecification)
    }

    override fun userSearch(rawSearchFields: Map<String, String>): Response<List<User>> {
        val searchFields = filterAndMapKeysToSearchFields(rawSearchFields)

        searchFields.filter { entry ->
            val userSearchField = entry.key
            val searchField = entry.value
            val validator = UserSearchFieldValidatorFactory(userSearchField).validator
            validator.validate(searchField)
        }

        val userSearchResults = preformUserSearch(searchFields)
        return ResponseCreation.success(responseObject = userSearchResults)
    }

    override fun userSearchAllFieldsMustBeValid(rawSearchFields: Map<String, String>): Response<List<User>> {
        val searchFields = filterAndMapKeysToSearchFields(rawSearchFields)

        val errors: MutableList<String> = mutableListOf()
        searchFields.forEach { (searchField, searchValue) ->
            val validator = UserSearchFieldValidatorFactory(searchField).validator
            val isValid = validator.validate(searchValue)
            if (!isValid) {
                errors += validator.getValidationError()
            }
        }
        val hasValidationErrors = errors.isNotEmpty()
        return if (hasValidationErrors) {
            ResponseCreation.error(errors = errors)
        } else {
            val searchResult = preformUserSearch(searchFields)
            ResponseCreation.success(responseObject = searchResult)
        }
    }

    private fun filterAndMapKeysToSearchFields(rawSearchFields: Map<String, String>): Map<UserSearchField, String> {
        return rawSearchFields.filterKeys {
            try {
                UserSearchField.valueOf(it)
                true
            } catch (exception: IllegalArgumentException) {
                false
            }
        }.mapKeys { UserSearchField.valueOf(it.key) }
    }

}