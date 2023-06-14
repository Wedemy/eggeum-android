/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.register_cafe.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import us.wedemy.eggeum.common.ui.base.BaseActivity
import us.wedemy.eggeum.register_cafe.R
import us.wedemy.eggeum.register_cafe.databinding.ActivityRegisterCafeBinding

class RegisterCafeActivity : BaseActivity<ActivityRegisterCafeBinding>(R.layout.activity_register_cafe) {

  override fun getViewBinding() = ActivityRegisterCafeBinding.inflate(layoutInflater)

  private val navController: NavController?
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    navController?.setGraph(R.navigation.nav_register_cafe)
  }

  override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() ?: false
}
