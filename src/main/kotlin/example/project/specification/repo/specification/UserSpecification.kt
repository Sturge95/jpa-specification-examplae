package example.project.specification.repo.specification

import example.project.specification.model.Address
import example.project.specification.model.User
import example.project.specification.model.enum.UserSearchField
import example.project.specification.repo.specification.factory.UserPredicateFactory
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification

object UserSpecification {

    fun ageGreaterThanOrEqualTo(age: Int): Specification<User> {
        return Specification { root, query, criteriaBuilder ->
            val agePath = root.get<Int>("age")
            criteriaBuilder.greaterThanOrEqualTo(agePath, age)
        }
    }

    fun usersLivingOnStreet(street: String): Specification<User> {
        return Specification { root, query, criteriaBuilder ->
            val addressJoin = root.join<Address, User>("address", JoinType.INNER)
            val streetField = addressJoin.get<String>("street")
            criteriaBuilder.equal(streetField, street)
        }
    }


    /**
     *  Carry out an AND search on all the fields passed into the map, it's expected
     *  that before calling this method fields will have been validated.
     */
    fun andSearch(searchFields: Map<UserSearchField, String>): Specification<User> {
        return Specification { root, query, criteriaBuilder ->

            val searchPredicates = searchFields.map { entry ->
                val searchField = entry.key
                val searchValue = entry.value

                val predicateFactory = UserPredicateFactory(searchField, root, criteriaBuilder)
                predicateFactory.getPredicate(searchValue)
            }

            criteriaBuilder.and(*searchPredicates.toTypedArray())
        }
    }

}
