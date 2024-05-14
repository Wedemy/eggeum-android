package us.wedemy.eggeum.android.common.util

import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ErrorHandlerActions {
  fun showServerErrorToast()
  fun showNetworkErrorToast()
  fun handleNotFoundException()
  fun handleRefreshTokenExpired()
}

fun handleException(exception: Throwable, actions: ErrorHandlerActions) {
  when (exception) {
    is HttpException -> {
      if (exception.code() in 500..511) {
        actions.showServerErrorToast()
      } else if (exception.code() == 404) {
        actions.handleNotFoundException()
      } else if (exception == RefreshTokenExpiredException) {
        actions.handleRefreshTokenExpired()
      } else {
        Timber.e(exception)
      }
    }

    is UnknownHostException -> {
      actions.showNetworkErrorToast()
    }

    is SocketTimeoutException -> {
      actions.showServerErrorToast()
    }

    else -> {
      Timber.e(exception)
    }
  }
}
