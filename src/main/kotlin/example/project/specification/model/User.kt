package example.project.specification.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var age: Int,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var dateOfBirth: LocalDateTime,
    @ManyToOne
    @JoinColumn(name = "address_id")
    var address: Address
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (age != other.age) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (phoneNumber != other.phoneNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + age
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        return result
    }
}

@Entity
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var lineOne: String,
    var lineTwo: String? = null,
    var street: String,
    var country: String,
    var postcode: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (id != other.id) return false
        if (lineOne != other.lineOne) return false
        if (lineTwo != other.lineTwo) return false
        if (street != other.street) return false
        if (country != other.country) return false
        if (postcode != other.postcode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + lineOne.hashCode()
        result = 31 * result + (lineTwo?.hashCode() ?: 0)
        result = 31 * result + street.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + postcode.hashCode()
        return result
    }
}