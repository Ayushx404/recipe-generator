package com.ayush.recipegenerator.common.util

/**
 * A sealed class that represents the different states of a resource.
 * @param T The type of data being wrapped by the resource.
 * @property data The data associated with the resource, nullable.
 * @property message An optional message associated with the resource, nullable.
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
) {
    /**
     * Represents a successful outcome.
     * @param data The data associated with the success, not nullable.
     * @param message An optional message associated with the success, nullable.
     */
    class Success<T>(data: T, message: String? = null) : Resource<T>(data, message)

    /**
     * Represents a loading state.
     */
    class Loading<T> : Resource<T>()

    /**
     * Represents a failed outcome.
     * @param message An optional error message associated with the failure, nullable.
     * @param data The data associated with the failure, nullable.
     */
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
}
