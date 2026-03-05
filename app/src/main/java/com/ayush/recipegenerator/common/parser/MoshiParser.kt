package com.ayush.recipegenerator.common.parser

import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import javax.inject.Inject

class MoshiParser
    @Inject
    constructor(
        private val moshi: Moshi,
    ) : JsonParser {
        override fun <T> fromJson(
            json: String,
            type: Type,
        ): T? {
            val adapter = moshi.adapter<T>(type)
            return adapter.fromJson(json)
        }

        override fun <T> toJson(
            obj: T,
            type: Type,
        ): String? {
            val adapter = moshi.adapter<T>(type)
            return adapter.toJson(obj)
        }
    }
