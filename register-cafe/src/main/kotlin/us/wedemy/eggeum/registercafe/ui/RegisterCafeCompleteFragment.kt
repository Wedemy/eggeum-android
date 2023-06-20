/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.registercafe.ui

import android.os.Bundle
import android.view.View
import us.wedemy.eggeum.common.ui.base.BaseFragment
import us.wedemy.eggeum.registercafe.databinding.FragmentRegisterCafeCompleteBinding
import us.wedemy.eggeum.registercafe.R

class RegisterCafeCompleteFragment :
  BaseFragment<FragmentRegisterCafeCompleteBinding>(R.layout.fragment_register_cafe_complete) {

  override fun getViewBinding() = FragmentRegisterCafeCompleteBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
  }

  private fun initListener() {
    binding.btnRegisterCafeComplete.setOnClickListener {
      requireActivity().finish()
    }
  }
}
