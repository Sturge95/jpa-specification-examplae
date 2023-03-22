package example.project.specification.repo.specification

import example.project.specification.model.User
import org.springframework.data.jpa.domain.Specification

object UserSpecification {

    fun ageGreaterThanOrEqualTo(age: Int): Specification<User> {
        return Specification { root, query, criteriaBuilder ->
            val agePath = root.get<Int>("age")
            criteriaBuilder.greaterThanOrEqualTo(agePath, age)
        }
    }

}