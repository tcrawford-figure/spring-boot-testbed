package com.example.pg.dbconfig.controller

import com.example.pg.dbconfig.data.UserCreateRequest
import com.example.pg.dbconfig.data.UserRepository
import com.example.pg.dbconfig.data.UserUpdateRequest
import com.example.pg.dbconfig.domain.UserId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userRepository: UserRepository,
) {
    // Read User
    @GetMapping("/{id}")
    suspend fun findUserById(
        @PathVariable id: Long
    ): ResponseEntity<UserResponse> {
        val user = userRepository.findUserById(UserId(id))

        return if (user != null) {
            ResponseEntity.ok(
                UserResponse(
                    id = user.id.value,
                    name = user.name,
                    age = user.age,
                )
            )
        } else {
            ResponseEntity.notFound().build()
        }
    }

    data class UserResponse(
        val id: Long,
        val name: String,
        val age: Int,
    )

    // Create User
    @PostMapping
    suspend fun create(
        @RequestBody form: UserCreateRequestForm
    ): ResponseEntity<UserCreateResponse> {
        val userId = userRepository.create(
            UserCreateRequest(
                name = form.name,
                age = form.age,
            )
        )

        return ResponseEntity.ok(
            UserCreateResponse(
                id = userId.value,
            )
        )
    }

    data class UserCreateRequestForm(
        val name: String,
        val age: Int,
    )

    data class UserCreateResponse(val id: Long)

    // Update User
    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: Long,
        @RequestBody form: UserUpdateRequestForm
    ): ResponseEntity<Unit> {
        userRepository.update(
            id = id,
            request = UserUpdateRequest(
                name = form.name,
                age = form.age,
            )
        )

        return ResponseEntity.ok().build()
    }

    data class UserUpdateRequestForm(
        val name: String? = null,
        val age: Int? = null,
    )

    // Delete User
    @DeleteMapping("/{id}")
    suspend fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        userRepository.delete(UserId(id))

        return ResponseEntity.noContent().build()
    }
}
