package com.enzoftware.androidgraphql.core

import androidx.annotation.StringRes
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.enzoftware.androidgraphql.R


/**
 * Created by Enzo Lizama Paredes on 12/2/20.
 * Contact: lizama.enzo@gmail.com
 */
class ApolloExceptionHandler {

    fun cause(throwable: Throwable) = when (throwable) {
        is ApolloHttpException -> apolloHttpException(throwable.code())
        is ApolloNetworkException -> ApolloRequestException.ConnectionNetwork()
        else -> ApolloRequestException.Unknown()
    }

    private fun apolloHttpException(code: Int) = when {
        code == AUTHENTICATION_CODE -> ApolloRequestException.Authentication()
        code == ACCESS_DENIED_CODE -> ApolloRequestException.AccessDenied()
        code >= UNAVAILABLE_SERVER_CODE -> ApolloRequestException.UnavailableServer()
        else -> ApolloRequestException.Unknown()
    }

    companion object {
        const val AUTHENTICATION_CODE = 401
        const val ACCESS_DENIED_CODE = 403
        const val UNAVAILABLE_SERVER_CODE = 500
    }
}


sealed class ApolloRequestException(@StringRes val error: Int) : Exception() {
    class Authentication : ApolloRequestException(R.string.error_authentication)
    class AccessDenied : ApolloRequestException(R.string.error_access_denied)
    class UnavailableServer : ApolloRequestException(R.string.error_unavailable_server)
    class ConnectionNetwork : ApolloRequestException(R.string.error_connection_network)
    class Unknown : ApolloRequestException(R.string.error_unknown)
}

