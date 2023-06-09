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
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import us.wedemy.eggeum.onboard.R
import us.wedemy.eggeum.onboard.databinding.FragmentRegisterAccountBinding
import us.wedemy.eggeum.onboard.ui.base.BaseFragment
import us.wedemy.eggeum.onboard.util.ViewModelFactory
import us.wedemy.eggeum.onboard.util.repeatOnStarted
import us.wedemy.eggeum.onboard.util.safeNavigate
import us.wedemy.eggeum.onboard.viewmodel.RegisterAccountViewModel

class RegisterAccountFragment : BaseFragment<FragmentRegisterAccountBinding>(R.layout.fragment_register_account) {

  override fun getViewBinding() = FragmentRegisterAccountBinding.inflate(layoutInflater)

  private lateinit var viewModel: RegisterAccountViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val savedStateHandle = SavedStateHandle()
    val viewModelFactory = ViewModelFactory(savedStateHandle)
    viewModel = ViewModelProvider(this, viewModelFactory)[RegisterAccountViewModel::class.java]

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

      cbAgreeToServiceTerms.setOnCheckedChangeListener { _, isChecked ->
        viewModel.setCbAgreeToServiceTerms(isChecked)
      }

      cbAgreeToCollectPersonalInfo.setOnCheckedChangeListener { _, isChecked ->
        viewModel.setCbAgreeToCollectPersonalInfo(isChecked)
      }

      cbAgreeToProvidePersonalInfoTo3rdParty.setOnCheckedChangeListener { _, isChecked ->
        viewModel.setCbAgreeToProvidePersonalInfoTo3rdParty(isChecked)
      }

      cbOver14YearOld.setOnCheckedChangeListener { _, isChecked ->
        viewModel.setCbOver14YearOld(isChecked)
      }

      cbWouldLikeToReceiveInfoAboutNewCafeAndEvents.setOnCheckedChangeListener { _, isChecked ->
        viewModel.setCbWouldLikeToReceiveInfoAboutNewCafeAndEvents(isChecked)
      }

      cbAgreeToAllRequiredItems.setOnClickListener {
        val currentState = cbAgreeToAllRequiredItems.isChecked
        viewModel.setCbAgreeToAllRequiredItem(currentState)
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
        viewModel.over14YearOld.collect { isChecked ->
          binding.cbOver14YearOld.isChecked = isChecked
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