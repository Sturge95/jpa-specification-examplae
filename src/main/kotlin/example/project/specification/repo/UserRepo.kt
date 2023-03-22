package example.project.specification.repo

import example.project.specification.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<User, Long>, JpaSpecificationExecutor<User> 
