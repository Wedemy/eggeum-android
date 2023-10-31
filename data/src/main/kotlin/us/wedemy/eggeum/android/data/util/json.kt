/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonWriter
import okio.Buffer
import us.wedemy.eggeum.android.domain.EggeumDsl

@EggeumDsl
internal interface JsonBuilder {
  infix fun String.withInt(value: Int?)
  infix fun String.withFloat(value: Float?)
  infix fun String.withBoolean(value: Boolean?)
  infix fun String.withString(value: String?)
  infix fun String.withDouble(value: Double?)
  fun <T> String.withPojo(adapter: JsonAdapter<T>, value: T?)

  infix fun String.withInts(value: List<Int>)
  infix fun String.withFloats(value: List<Float>)
  infix fun String.withBooleans(value: List<Boolean>)
  infix fun String.withStrings(value: List<String>)
  infix fun String.withDoubles(value: List<Double>)
  fun <T> String.withPojos(adapter: JsonAdapter<List<T?>>, value: List<T?>?)

  fun build(): String
}

// 매 DSL 마다 `buffer` 및 `writer` 인스턴스를 새로 만들어야 함
// -> 싱글톤 불가
internal class JsonBuilderInstance(private val pretty: Boolean) : JsonBuilder {
  private val buffer = Buffer()
  private val writer =
    JsonWriter
      .of(buffer)
      .apply {
        if (pretty) indent = "  "
        serializeNulls = true
      }
      .also { it.beginObject() }

  override fun String.withInt(value: Int?) {
    writer.name(this).value(value)
  }

  override fun String.withFloat(value: Float?) {
    writer.name(this).value(value)
  }

  override fun String.withBoolean(value: Boolean?) {
    writer.name(this).value(value)
  }

  override fun String.withString(value: String?) {
    writer.name(this).value(value)
  }

  override fun String.withDouble(value: Double?) {
    writer.name(this).value(value)
  }

  override fun <T> String.withPojo(adapter: JsonAdapter<T>, value: T?) {
    writer.name(this)
    adapter.serializeNulls().toJson(writer, value)
  }

  override fun String.withInts(value: List<Int>) {
    writer.name(this)
    writer.beginArray()
    for (element in value) {
      writer.value(element)
    }
    writer.endArray()
  }

  override fun String.withFloats(value: List<Float>) {
    writer.name(this)
    writer.beginArray()
    for (element in value) {
      writer.value(element)
    }
    writer.endArray()
  }

  override fun String.withBooleans(value: List<Boolean>) {
    writer.name(this)
    writer.beginArray()
    for (element in value) {
      writer.value(element)
    }
    writer.endArray()
  }

  override fun String.withStrings(value: List<String>) {
    writer.name(this)
    writer.beginArray()
    for (element in value) {
      writer.value(element)
    }
    writer.endArray()
  }

  override fun String.withDoubles(value: List<Double>) {
    writer.name(this)
    writer.beginArray()
    for (element in value) {
      writer.value(element)
    }
    writer.endArray()
  }

  override fun <T> String.withPojos(adapter: JsonAdapter<List<T?>>, value: List<T?>?) {
    writer.name(this)
    adapter.serializeNulls().toJson(writer, value)
  }

  // https://github.com/JakeWharton/asciinema-vsync/blob/17ae722af6848aca41ce2964ad835cb1455dd13d/src/main/kotlin/com/jakewharton/asciinema/vsync/asciinemaVsync.kt#L24
  override fun build(): String {
    writer.endObject().close()
    return buffer.readUtf8()
  }
}

internal inline fun buildJson(pretty: Boolean = false, builder: JsonBuilder.() -> Unit) =
  with(JsonBuilderInstance(pretty)) { builder(); build() }
