/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "places")
internal data class PlaceResponse(
  @SerialName("address1")
  val address1: String?,

  @SerialName("address2")
  val address2: String?,

  @PrimaryKey(autoGenerate = false)
  @SerialName("id")
  val id: Long,

  @SerialName("image")
  val image: Image?,

  @SerialName("info")
  val info: Info?,

  @SerialName("latitude")
  val latitude: Double?,

  @SerialName("longitude")
  val longitude: Double?,

  @SerialName("menu")
  val menu: Menu?,

  @SerialName("name")
  val name: String,

  @SerialName("type")
  val type: String?,
)
