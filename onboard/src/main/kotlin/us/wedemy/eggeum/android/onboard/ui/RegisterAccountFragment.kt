/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.onboard.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.onboard.databinding.FragmentRegisterAccountBinding
import us.wedemy.eggeum.android.onboard.viewmodel.OnBoardViewModel

@AndroidEntryPoint
class RegisterAccountFragment : BaseFragment<FragmentRegisterAccountBinding>() {
  override fun getViewBinding() = FragmentRegisterAccountBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<OnBoardViewModel>()

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
        val action = RegisterAccountFragmentDirections.actionFragmentRegisterAccountToFragmentServiceTerms()
        findNavController().safeNavigate(action)
      }

      tvAgreeToCollectPersonalInfoDetail.setOnClickListener {
        val action = RegisterAccountFragmentDirections.actionFragmentRegisterAccountToFragmentServiceTerms()
        findNavController().safeNavigate(action)
      }

      tvAgreeToProvidePersonalInfoTo3rdPartyDetail.setOnClickListener {
        val action = RegisterAccountFragmentDirections.actionFragmentRegisterAccountToFragmentServiceTerms()
        findNavController().safeNavigate(action)
      }

      btnRegisterAccount.setOnClickListener {
        val action = RegisterAccountFragmentDirections.actionFragmentRegisterAccountToFragmentRegisterNickname()
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
        viewModel.agreeMarketing.collect { isChecked ->
          binding.cbWouldLikeToReceiveInfoAboutNewCafeAndEvents.isChecked = isChecked
        }
      }

      launch {
        viewModel.enableRegisterAccount.collect { flag ->
          binding.btnRegisterAccount.isEnabled = flag
          binding.tvPleaseToAgreeAllRequiredItem.isInvisible = flag
          binding.cbAgreeToAllRequiredItems.isChecked = flag
        }
      }
    }
  }
}
