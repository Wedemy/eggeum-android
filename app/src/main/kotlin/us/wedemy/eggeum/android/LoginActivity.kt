/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android

import android.app.Activity
import android.content.IntentSender
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import us.wedemy.eggeum.android.databinding.ActivityLoginBinding
import us.wedemy.eggeum.android.common.ui.BaseActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
  override val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

  override var statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  override var navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

  private lateinit var oneTapClient: SignInClient
  private lateinit var signInRequest: BeginSignInRequest

  private val oneTapClientResult =
    registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
      if (result.resultCode == Activity.RESULT_OK) {
        try {
          val credential = oneTapClient.getSignInCredentialFromIntent(result.data)

          @Suppress("UNUSED_VARIABLE")
          val idToken = credential.googleIdToken.also { idToken ->
            Timber.tag("idToken").d(idToken)
          }
        } catch (exception: ApiException) {
          Timber.e(exception.localizedMessage)
        }
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initGoogleLogin()
    initListener()
  }

  private fun initGoogleLogin() {
    oneTapClient = Identity.getSignInClient(this)
    signInRequest = BeginSignInRequest.builder()
      .setPasswordRequestOptions(
        BeginSignInRequest.PasswordRequestOptions.builder()
          .setSupported(true)
          .build(),
      )
      .setGoogleIdTokenRequestOptions(
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
          .setSupported(true)
          .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
          .setFilterByAuthorizedAccounts(false)
          .build(),
      )
      .setAutoSelectEnabled(true)
      .build()
  }

  private fun initListener() {
    binding.cvGoogleLogin.setOnClickListener {
      oneTapClient
        .beginSignIn(signInRequest)
        .addOnSuccessListener(this) { result ->
          try {
            oneTapClientResult.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
          } catch (exception: IntentSender.SendIntentException) {
            Timber.e("Couldn't start One Tap UI: ${exception.localizedMessage}")
          }
        }
        .addOnFailureListener(this) { exception ->
          Timber.e(exception.localizedMessage)
        }
    }
  }
}
