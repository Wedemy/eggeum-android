/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.main.ui

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.main.databinding.FragmentMyAccountBinding

@AndroidEntryPoint
class MyAccountFragment : BaseFragment<FragmentMyAccountBinding>() {
  override fun getViewBinding() = FragmentMyAccountBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    //TODO 화면 전환 클릭 이벤트 리스너 구현
  }

  private fun initObserver() {
    //TODO 프로필 정보 및 버전 정보 가져오기
  }
}
