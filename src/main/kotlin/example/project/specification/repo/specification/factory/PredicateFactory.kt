package example.project.specification.repo.specification.factory

import example.project.specification.model.Address
import example.project.specification.model.User
import example.project.specification.model.enum.UserSearchField
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import java.time.LocalDateTime
import kotlin.reflect.KProperty1


class UserPredicateFactory(private val userSearchField: UserSearchField, private val root: Root<User>, private val criteriaBuilder: CriteriaBuilder) {

    private val predicateGenerator: PredicateGenerator;

    init {
        predicateGenerator = when (userSearchField) {
            UserSearchField.AGE,
            UserSearchField.FIRST_NAME,
            UserSearchField.LAST_NAME,
            UserSearchField.PHONE_NUMBER -> StringFieldPredicateNoJoin()

            UserSearchField.DATE_OF_BIRTH -> DateFieldPredicateNoJoin()
            UserSearchField.ADDRESS_POSTCODE,
            UserSearchField.ADDRESS_STREET -> StringPredicateWithAddressJoin(inPredicate = false)

            UserSearchField.ADDRESS_ID -> StringPredicateWithAddressJoin(inPredicate = true)
        }
    }

    fun getPredicate(searchString: String): Predicate {
        return predicateGenerator.createPredicate(searchString, userSearchField, root, criteriaBuilder)
    }

}

interface PredicateGenerator {

    fun createPredicate(searchString: String, searchField: UserSearchField, root: Root<User>, criteriaBuilder: CriteriaBuilder): Predicate

}

class StringFieldPredicateNoJoin() : PredicateGenerator {
    override fun createPredicate(searchString: String, searchField: UserSearchField, root: Root<User>, criteriaBuilder: CriteriaBuilder): Predicate {
        return criteriaBuilder.equal(root.get<String>(searchField.fieldOne.name), searchString)
    }
}

class StringPredicateWithAddressJoin(private val inPredicate: Boolean) : PredicateGenerator {
    override fun createPredicate(searchString: String, searchField: UserSearchField, root: Root<User>, criteriaBuilder: CriteriaBuilder): Predicate {
        val fieldValuesToSearch = searchString.split(",").map { it.toLong() }
        val joinToAddress = root.join<Address, User>(searchField.fieldOne.name)
        val addressFieldToSearch = joinToAddress.get<String>(searchField.fieldTwo!!.name)

        return if (inPredicate) {
            addressFieldToSearch.`in`(fieldValuesToSearch)
        } else {
            criteriaBuilder.equal(addressFieldToSearch, searchString)
        }
    }
}

class DateFieldPredicateNoJoin() : PredicateGenerator {
    override fun createPredicate(searchString: String, searchField: UserSearchField, root: Root<User>, criteriaBuilder: CriteriaBuilder): Predicate {
        val splitDates = searchString.split(",")
        val dateFrom = splitDates[0]
        val dateTo = splitDates[1]
        return PredicateUtils.dateBetween(dateFrom, dateTo, searchField.fieldOne, root, criteriaBuilder)
    }
}

object PredicateUtils {

    fun dateBetween(
        dateFrom: String, dateTo: String, field: KProperty1<*, *>, root: Root<User>, builder: CriteriaBuilder
    ): Predicate {

        return if (dateFrom.isBlank()) {
            builder.lessThan(root.get(field.name), LocalDateTime.parse(dateTo.trim()))
        } else if (dateTo.isBlank()) {
            builder.greaterThan(root.get(field.name), LocalDateTime.parse(dateFrom.trim()))
        } else {
            val dateField = root.get<LocalDateTime>(field.name)
            builder.between(
                dateField,
                LocalDateTime.parse(dateFrom.trim()),
                LocalDateTime.parse(dateTo.trim())
            )
        }
    }

}
