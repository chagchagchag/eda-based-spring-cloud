package net.spring.cloud.prototype.userservice.dataaccess.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import net.spring.cloud.prototype.dataaccess.entity.PrimaryKeyEntity

@Entity
@Table(name = "users")
class UserEntity (
    email: String,
    name: String,
    encryptedPassword: String,
    roles: String,
) : PrimaryKeyEntity(){

    @Column(nullable = false, length = 50, unique = true)
    var email: String = email
        protected set

    @Column(nullable = false, length = 50)
    var name: String = name
        protected set

    @Column(nullable = false, unique = true)
    var encryptedPassword: String = encryptedPassword
        protected set

    var roles: String = roles
        protected set

    fun getRoleList(): List<String>{
        return if(roles.length > 0)
            listOf(roles.split(","))
                .flatten()
                .toList()
        else
            listOf()
    }
}