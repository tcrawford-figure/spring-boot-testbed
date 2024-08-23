package com.example.pg.dbconfig.data

import com.example.pg.dbconfig.domain.User
import com.example.pg.dbconfig.domain.UserEntity
import com.example.pg.dbconfig.domain.UserId
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component

@Component
class UserRepository {
    // read user by user primary key
    suspend fun findUserById(id: UserId): User? =
        newSuspendedTransaction {
            UserEntity.selectAll().where { UserEntity.id eq id.value }.firstOrNull()?.let {
                User(
                    id = UserId(it[UserEntity.id].value),
                    name = it[UserEntity.name],
                    age = it[UserEntity.age],
                )
            }
        }

    // create user
    suspend fun create(request: UserCreateRequest): UserId =
        newSuspendedTransaction {
            val id = UserEntity.insertAndGetId {
                it[name] = request.name
                it[age] = request.age
            }

            UserId(id.value)
        }

    // update user
    suspend fun update(id: Long, request: UserUpdateRequest): Int =
        newSuspendedTransaction {
            UserEntity.update({ UserEntity.id eq id }) {
                request.name?.let { name -> it[UserEntity.name] = name }
                request.age?.let { age -> it[UserEntity.age] = age }
            }
        }

    // delete user
    suspend fun delete(id: UserId) =
        newSuspendedTransaction {
            UserEntity.deleteWhere { UserEntity.id eq id.value }
        }
}

data class UserCreateRequest(
    val name: String,
    val age: Int,
)

data class UserUpdateRequest(
    val name: String? = null,
    val age: Int? = null,
)
