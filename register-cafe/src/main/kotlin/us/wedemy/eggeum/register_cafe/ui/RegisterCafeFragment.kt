/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.register_cafe.ui

import us.wedemy.eggeum.common.ui.base.BaseFragment
import us.wedemy.eggeum.register_cafe.R
import us.wedemy.eggeum.register_cafe.databinding.FragmentRegisterCafeBinding

class RegisterCafeFragment : BaseFragment<FragmentRegisterCafeBinding>(R.layout.fragment_register_cafe) {

  override fun getViewBinding() = FragmentRegisterCafeBinding.inflate(layoutInflater)
}