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
import us.wedemy.eggeum.common.ui.BaseActivity

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
          val idToken = credential.googleIdToken
          if (idToken != null) {
            Timber.tag("idToken").d(idToken)
          }
        } catch (e: ApiException) {
          Timber.d(e.localizedMessage)
        }
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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
          // Your server's client ID, not your Android client ID.
          .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
          // Only show accounts previously used to sign in.
          .setFilterByAuthorizedAccounts(false)
          .build(),
      )
      // Automatically sign in when exactly one credential is retrieved.
      .setAutoSelectEnabled(true)
      .build()

    initListener()
  }

  private fun initListener() {
    binding.cvGoogleLogin.setOnClickListener {
      oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener(this) { result ->
          try {
            oneTapClientResult.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
          } catch (e: IntentSender.SendIntentException) {
            Timber.e("Couldn't start One Tap UI: ${e.localizedMessage}")
          }
        }
        .addOnFailureListener(this) { e ->
          // No saved credentials found. Launch the One Tap sign-up flow, or
          // do nothing and continue presenting the signed-out UI.
          Timber.d(e.localizedMessage)
        }
    }
  }
}
