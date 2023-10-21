/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused", "UnusedPrivateProperty")

package us.wedemy.eggeum.android.updatecafe.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentInputCafeInfoBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.InputCafeInfoViewModel

@AndroidEntryPoint
class InputCafeInfoFragment : BaseFragment<FragmentInputCafeInfoBinding>() {
  override fun getViewBinding() = FragmentInputCafeInfoBinding.inflate(layoutInflater)

  private val viewModel by viewModels<InputCafeInfoViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      tbSelectInputCafeInfo.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }
      tietInputCafeArea.doAfterTextChanged {
        Log.d("logcat", "면적: $it")
      }
      tietInputCafeMeetingRoom.doAfterTextChanged {
        Log.d("logcat", "회의실: $it")
      }
      tietInputCafeMultiSeat.doAfterTextChanged {
        Log.d("logcat", "다인석: $it")
      }
      tietInputCafeSingleSeat.doAfterTextChanged {
        Log.d("logcat", "1인석: $it")
      }
      tietInputCafeBusinessHours.doAfterTextChanged {
        Log.d("logcat", "영업시간: $it")
      }
    }
  }

  private fun initObserver() {
    // TODO
  }
}
