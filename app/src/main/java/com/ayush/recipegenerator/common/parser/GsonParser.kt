package com.ayush.recipegenerator.common.parser

import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

class GsonParser
    @Inject
    constructor(
        private val gson: Gson,
    ) : JsonParser {
        /**
         * This method deserializes the specified Json into an object of the specified type
         */
        override fun <T> fromJson(
            json: String,
            type: Type,
        ): T? {
            return gson.fromJson(json, type)
        }

        /**
         * This method serializes the specified object into its equivalent Json representation
         */
        override fun <T> toJson(
            obj: T,
            type: Type,
        ): String? {
            return gson.toJson(obj, type)
        }
    }
