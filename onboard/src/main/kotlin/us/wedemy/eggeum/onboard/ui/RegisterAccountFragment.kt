/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.common.extension.repeatOnStarted
import us.wedemy.eggeum.common.extension.safeNavigate
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.onboard.databinding.FragmentRegisterAccountBinding
import us.wedemy.eggeum.onboard.viewmodel.RegisterAccountViewModel

@AndroidEntryPoint
class RegisterAccountFragment : BaseFragment<FragmentRegisterAccountBinding>() {
  override fun getViewBinding() = FragmentRegisterAccountBinding.inflate(layoutInflater)

  private val viewModel by viewModels<RegisterAccountViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      tbRegisterAccount.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      clAgreeToServiceTerms.setOnClickListener {
        viewModel.setCbAgreeToServiceTerms()
      }

      clAgreeToCollectPersonalInfo.setOnClickListener {
        viewModel.setCbAgreeToCollectPersonalInfo()
      }

      clAgreeToProvidePersonalInfoTo3rdParty.setOnClickListener {
        viewModel.setCbAgreeToProvidePersonalInfoTo3rdParty()
      }

      clIsOver14YearOld.setOnClickListener {
        viewModel.setCbOver14YearOld()
      }

      clWouldLikeToReceiveInfoAboutNewCafeAndEvents.setOnClickListener {
        viewModel.setCbWouldLikeToReceiveInfoAboutNewCafeAndEvents()
      }

      clAgreeToAllRequiredItems.setOnClickListener {
        viewModel.setAgreeToAllRequiredItems()
      }

      tvAgreeToServiceTermsDetail.setOnClickListener {
        val action = RegisterAccountFragmentDirections.actionRegisterAccountFragmentToServiceTermsFragment()
        findNavController().safeNavigate(action)
      }

      tvAgreeToCollectPersonalInfoDetail.setOnClickListener {
        val action = RegisterAccountFragmentDirections.actionRegisterAccountFragmentToServiceTermsFragment()
        findNavController().safeNavigate(action)
      }

      tvAgreeToProvidePersonalInfoTo3rdPartyDetail.setOnClickListener {
        val action = RegisterAccountFragmentDirections.actionRegisterAccountFragmentToServiceTermsFragment()
        findNavController().safeNavigate(action)
      }

      btnRegisterNickname.setOnClickListener {
        val action = RegisterAccountFragmentDirections.actionRegisterAccountFragmentToRegisterNicknameFragment()
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.agreeToServiceTerms.collect { isChecked ->
          binding.cbAgreeToServiceTerms.isChecked = isChecked
        }
      }

      launch {
        viewModel.agreeToCollectPersonalInfo.collect { isChecked ->
          binding.cbAgreeToCollectPersonalInfo.isChecked = isChecked
        }
      }

      launch {
        viewModel.agreeToProvidePersonalInfoTo3rdParty.collect { isChecked ->
          binding.cbAgreeToProvidePersonalInfoTo3rdParty.isChecked = isChecked
        }
      }

      launch {
        viewModel.isOver14YearOld.collect { isChecked ->
          binding.cbIsOver14YearOld.isChecked = isChecked
        }
      }

      launch {
        viewModel.wouldLikeToReceiveInfoAboutNewCafeAndEvents.collect { isChecked ->
          binding.cbWouldLikeToReceiveInfoAboutNewCafeAndEvents.isChecked = isChecked
        }
      }

      launch {
        viewModel.enableRegisterAccount.collect { flag ->
          binding.btnRegisterNickname.isEnabled = flag
          binding.tvPleaseToAgreeAllRequiredItem.isInvisible = flag
          binding.cbAgreeToAllRequiredItems.isChecked = flag
        }
      }
    }
  }
}
