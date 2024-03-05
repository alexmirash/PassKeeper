package com.mirash.familiar.db

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Mirash
 */
data class UserWithCredentials(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val credentials: List<Credentials>
)
