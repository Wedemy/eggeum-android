/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.ui

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.BuildConfig
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseActivity
import us.wedemy.eggeum.android.databinding.ActivityLoginBinding
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.onboard.ui.OnboardActivity
import us.wedemy.eggeum.android.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
  override val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

  override var statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  override var navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

  private val viewModel by viewModels<LoginViewModel>()

  private lateinit var oneTapClient: SignInClient
  private lateinit var signInRequest: BeginSignInRequest
  private lateinit var idToken: String

  // TODO 네트워크 연결 문제 관련 토스트 출력
  private val oneTapClientResult =
    registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
      if (result.resultCode == Activity.RESULT_OK) {
        try {
          val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
          val googleIdToken = credential.googleIdToken
          if (googleIdToken != null) {
            Firebase.auth.currentUser!!.getIdToken(true)
              .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                  idToken = task.result.token!!
                  viewModel.getLoginBody(idToken)
                } else {
                  Timber.e(task.exception)
                }
              }
          } else {
            Timber.d("No ID token!")
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
    initObserver()
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

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToMainEvent.collect {
          startActivity(Intent(this@LoginActivity, MainActivity::class.java))
          finish()
        }
      }

      launch {
        viewModel.navigateToOnBoardingEvent.collect {
          val intent = Intent(this@LoginActivity, OnboardActivity::class.java)
          intent.putExtra("id_token", idToken)
          startActivity(intent)
          finish()
        }
      }

      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}
