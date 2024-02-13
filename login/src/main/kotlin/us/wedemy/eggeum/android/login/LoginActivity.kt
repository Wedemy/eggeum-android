/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.base.BaseActivity
import us.wedemy.eggeum.android.common.util.setOnSingleClickListener
import us.wedemy.eggeum.android.login.databinding.ActivityLoginBinding
import us.wedemy.eggeum.android.navigator.MainNavigator
import us.wedemy.eggeum.android.navigator.OnboardNavigator

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
  override val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

  override var statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  override var navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

  private val viewModel by viewModels<LoginViewModel>()

  @Inject
  lateinit var mainNavigator: MainNavigator

  @Inject
  lateinit var onboardNavigator: OnboardNavigator

//  private lateinit var oneTapClient: SignInClient
//  private lateinit var signInRequest: BeginSignInRequest
  private lateinit var idToken: String

  private lateinit var googleSignInClient: GoogleSignInClient
  private lateinit var loginLauncher: ActivityResultLauncher<Intent>

  // TODO 네트워크 연결 문제 관련 토스트 출력
//  private val oneTapClientResult =
//    registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
//      if (result.resultCode == Activity.RESULT_OK) {
//        try {
//          // 인텐트로 부터 로그인 자격 정보를 가져옴
//          val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
//          // 가져온 자격 증명에서 Google ID 토큰을 추출
//          val googleIdToken = credential.googleIdToken
//          if (googleIdToken != null) {
//            // Google ID 토큰을 사용해 Firebase 인증 자격 증명을 생성
//            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
//            // 생성된 Firebase 인증 자격 증명을 사용하여 Firebase 에 로그인을 시도
//            Firebase.auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
//              if (task.isSuccessful) {
//                Firebase.auth.currentUser?.getIdToken(true)?.addOnCompleteListener { idTokenTask ->
//                  if (idTokenTask.isSuccessful) {
//                    idTokenTask.result?.token?.let { token ->
//                      idToken = token
//                      viewModel.login(idToken)
//                    } ?: Timber.e("FirebaseIdToken is null.")
//                  }
//                }
//              } else {
//                Timber.e(task.exception)
//              }
//            }
//          } else {
//            Timber.e("GoogleIdToken is null.")
//          }
//        } catch (exception: ApiException) {
//          Timber.e(exception.localizedMessage)
//        }
//      }
//    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.root.applyInsetter {
      type(
        ime = false,
        statusBars = true,
        navigationBars = true,
        f = InsetterApplyTypeDsl::padding,
      )
    }
    initGoogleLogin()
    initListener()
    initObserver()
  }

  private fun initGoogleLogin() {
    // 현쟈 액티비티(this)에 대한 Google One Tap 로그인 클라이언트를 가져옴
//    oneTapClient = Identity.getSignInClient(this)
//    signInRequest = BeginSignInRequest.builder()
//      .setGoogleIdTokenRequestOptions(
//        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//          // Google Id Token 기반 로그인을 지원하도록 설정
//          .setSupported(true)
//          // 서버의 클라이언트 ID 를 설정
//          .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
//          // 기존에 인증된 계정만을 필터링하지 않도록 설정
//          .setFilterByAuthorizedAccounts(false)
//          .build(),
//      )
//      // 이전에 선택 했던 계정을 기억
//      .setAutoSelectEnabled(true)
//      .build()

    val googleClientId = BuildConfig.GOOGLE_CLIENT_ID
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
      .requestIdToken(googleClientId)
      .requestServerAuthCode(googleClientId)
      .requestEmail()
      .build()

    loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == Activity.RESULT_OK) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
      }
    }

    googleSignInClient = GoogleSignIn.getClient(this, gso)
  }

  private fun initListener() {
    binding.cvGoogleLogin.setOnSingleClickListener {
//      oneTapClient
//        .beginSignIn(signInRequest)
//        .addOnSuccessListener(this) { result ->
//          try {
//            oneTapClientResult.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
//          } catch (exception: IntentSender.SendIntentException) {
//            Timber.e("Couldn't start One Tap UI: ${exception.localizedMessage}")
//          }
//        }
//        .addOnFailureListener(this) { exception ->
//          Timber.e(exception.localizedMessage)
//        }
      val signInIntent = googleSignInClient.signInIntent
      loginLauncher.launch(signInIntent)
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToMainEvent.collect {
          mainNavigator.navigateFrom(
            activity = this@LoginActivity,
            withFinish = true,
          )
        }
      }

      launch {
        viewModel.navigateToOnBoardingEvent.collect {
          onboardNavigator.navigateFrom(
            activity = this@LoginActivity,
            intentBuilder = {
              putExtra("id_token", idToken)
            },
            withFinish = true,
          )
        }
      }

      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val googleIdToken = completedTask.getResult(ApiException::class.java)?.idToken
      if (googleIdToken != null) {
        // Google ID 토큰을 사용해 Firebase 인증 자격 증명을 생성
        val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        // 생성된 Firebase 인증 자격 증명을 사용하여 Firebase 에 로그인을 시도
        Firebase.auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
          if (task.isSuccessful) {
            Firebase.auth.currentUser?.getIdToken(true)?.addOnCompleteListener { idTokenTask ->
              if (idTokenTask.isSuccessful) {
                idTokenTask.result?.token?.let { token ->
                  idToken = token
                  viewModel.login(idToken)
                } ?: Timber.e("FirebaseIdToken is null.")
              }
            }
          } else {
            Timber.e(task.exception)
          }
        }
      } else {
        Timber.e("GoogleIdToken is null.")
      }
    } catch (exception: ApiException) {
      Timber.e(exception.localizedMessage)
    }
  }
}
