/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import us.wedemy.eggeum.android.data.util.buildJson

@JsonClass(generateAdapter = true)
data class Pojo(
  val string: String?,
  val list: List<Any>?,
  val nested: Pojo? = null,
)

class JsonBuilderTest : FreeSpec() {
  private val moshi = Moshi.Builder().build()

  init {
    "pretty" - {
      "single json" {
        val actual =
          buildJson(pretty = true) {
            "int" withInt 1
            "float" withFloat 1f
            "boolean" withBoolean true
            "string" withString "string"
            "pojo".withPojo(moshi.adapter(), Pojo("1", listOf("2")))
          }
        val expected =
          "{\n" +
            "  \"int\": 1,\n" +
            "  \"float\": 1.0,\n" +
            "  \"boolean\": true,\n" +
            "  \"string\": \"string\",\n" +
            "  \"pojo\": {\n" +
            "    \"string\": \"1\",\n" +
            "    \"list\": [\n" +
            "      \"2\"\n" +
            "    ],\n" +
            "    \"nested\": null\n" +
            "  }\n" +
            "}"

        actual shouldBe expected
      }

      "double json" {
        val actual =
          buildJson(pretty = true) {
            "ints" withInts listOf(1, 2)
            "floats" withFloats listOf(1f, 2f)
            "booleans" withBooleans listOf(true, false)
            "strings" withStrings listOf("string", "string2")
            "pojos"
              .withPojos(
                moshi.adapter(Types.newParameterizedType(List::class.java, Pojo::class.java)),
                listOf(
                  Pojo("1", listOf("2")),
                  Pojo("3", listOf("4")),
                ),
              )
          }
        val expected =
          "{\n" +
            "  \"ints\": [\n" +
            "    1,\n" +
            "    2\n" +
            "  ],\n" +
            "  \"floats\": [\n" +
            "    1.0,\n" +
            "    2.0\n" +
            "  ],\n" +
            "  \"booleans\": [\n" +
            "    true,\n" +
            "    false\n" +
            "  ],\n" +
            "  \"strings\": [\n" +
            "    \"string\",\n" +
            "    \"string2\"\n" +
            "  ],\n" +
            "  \"pojos\": [\n" +
            "    {\n" +
            "      \"string\": \"1\",\n" +
            "      \"list\": [\n" +
            "        \"2\"\n" +
            "      ],\n" +
            "      \"nested\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"string\": \"3\",\n" +
            "      \"list\": [\n" +
            "        \"4\"\n" +
            "      ],\n" +
            "      \"nested\": null\n" +
            "    }\n" +
            "  ]\n" +
            "}"

        actual shouldBe expected
      }

      "nested pojo json" {
        val actual =
          buildJson(pretty = true) {
            "int" withInt 1
            "float" withFloat 1f
            "boolean" withBoolean true
            "string" withString "string"
            "pojo"
              .withPojo(
                moshi.adapter(),
                Pojo(
                  "1",
                  listOf("2"),
                  Pojo(
                    "3",
                    listOf("4"),
                  ),
                ),
              )
          }
        val expected =
          "{\n" +
            "  \"int\": 1,\n" +
            "  \"float\": 1.0,\n" +
            "  \"boolean\": true,\n" +
            "  \"string\": \"string\",\n" +
            "  \"pojo\": {\n" +
            "    \"string\": \"1\",\n" +
            "    \"list\": [\n" +
            "      \"2\"\n" +
            "    ],\n" +
            "    \"nested\": {\n" +
            "      \"string\": \"3\",\n" +
            "      \"list\": [\n" +
            "        \"4\"\n" +
            "      ],\n" +
            "      \"nested\": null\n" +
            "    }\n" +
            "  }\n" +
            "}"

        actual shouldBe expected
      }
    }

    "unpretty" - {
      "single json" {
        val actual =
          buildJson(pretty = false) {
            "int" withInt 1
            "float" withFloat 1f
            "boolean" withBoolean true
            "string" withString "string"
            "pojo".withPojo(moshi.adapter(), Pojo("1", listOf("2")))
          }
        val expected =
          "{" +
            "\"int\":1," +
            "\"float\":1.0," +
            "\"boolean\":true," +
            "\"string\":\"string\"," +
            "\"pojo\":{\"string\":\"1\",\"list\":[\"2\"]," +
            "\"nested\":null}" +
            "}"

        actual shouldBe expected
      }

      "double json" {
        val actual =
          buildJson(pretty = false) {
            "ints" withInts listOf(1, 2)
            "floats" withFloats listOf(1f, 2f)
            "booleans" withBooleans listOf(true, false)
            "strings" withStrings listOf("string", "string2")
            "pojos"
              .withPojos(
                moshi.adapter(Types.newParameterizedType(List::class.java, Pojo::class.java)),
                listOf(
                  Pojo("1", listOf("2")),
                  Pojo("3", listOf("4")),
                ),
              )
          }
        val expected =
          "{" +
            "\"ints\":[1,2]," +
            "\"floats\":[1.0,2.0]," +
            "\"booleans\":[true,false]," +
            "\"strings\":[\"string\",\"string2\"]," +
            "\"pojos\":[" +
            "{\"string\":\"1\",\"list\":[\"2\"],\"nested\":null}," +
            "{\"string\":\"3\",\"list\":[\"4\"],\"nested\":null}" +
            "]" +
            "}"

        actual shouldBe expected
      }

      "nested pojo json" {
        val actual =
          buildJson(pretty = false) {
            "int" withInt 1
            "float" withFloat 1f
            "boolean" withBoolean true
            "string" withString "string"
            "pojo"
              .withPojo(
                moshi.adapter(),
                Pojo(
                  "1",
                  listOf("2"),
                  Pojo(
                    "3",
                    listOf("4"),
                  ),
                ),
              )
          }
        val expected =
          "{" +
            "\"int\":1," +
            "\"float\":1.0," +
            "\"boolean\":true," +
            "\"string\":\"string\"," +
            "\"pojo\":{" +
            "\"string\":\"1\"," +
            "\"list\":[\"2\"]," +
            "\"nested\":{" +
            "\"string\":\"3\"," +
            "\"list\":[\"4\"]," +
            "\"nested\":null" +
            "}" +
            "}" +
            "}"

        actual shouldBe expected
      }
    }
  }
}
