package com.enzoftware.androidgraphql.core

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import com.enzoftware.androidgraphql.BuildConfig
import java.io.IOException


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */

suspend fun <D : Operation.Data, T : Any, V : Operation.Variables> ApolloClient.fetch(
    query: Query<D, T, V>,
    authToken: String = BuildConfig.TOKEN
): Result<T> {
    try {
        val apolloQueryCall = if (authToken.isNotEmpty()) {
            query(query).authorization(authToken)
        } else {
            query(query)
        }
        val response = apolloQueryCall.toDeferred().await()
        val data = response.data()
        val error = response.errors()
        if (!response.hasErrors() && data != null) {
            return Result.Success(data)
        }
        return Result.Error(
            IOException(
                error?.first()?.message() ?: "${
                    response.operation().name()
                } request success with errors"
            )
        )
    } catch (apolloException: ApolloException) {
        return Result.Error(apolloException.toApolloRequestException())
    }
}

private fun <T : Any> ApolloQueryCall<T>.authorization(token: String) =
    requestHeaders(RequestHeaders.builder().addHeader(AUTHORIZATION, token).build())

private fun ApolloException.toApolloRequestException() = ApolloExceptionHandler().cause(this)

const val HTTP_REQUEST_TIMEOUT_SECONDS: Long = 30
const val JWT = "JWT "
const val AUTHORIZATION: String = "Authorization"