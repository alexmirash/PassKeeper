package com.mirash.familiar.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mirash.familiar.model.credentials.CredentialsModel
import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Credentials(var userId: Long = UserControl.userId) : CredentialsModel() {
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0
}