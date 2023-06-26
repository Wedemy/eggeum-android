/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android

import us.wedemy.eggeum.android.databinding.ActivityLoginBinding
import us.wedemy.eggeum.common.ui.BaseActivity

class LoginActivity : BaseActivity() {
  override val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
}