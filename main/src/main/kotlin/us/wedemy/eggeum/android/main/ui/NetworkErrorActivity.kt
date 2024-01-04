/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.os.Bundle
import us.wedemy.eggeum.android.common.base.BaseActivity
import us.wedemy.eggeum.android.main.databinding.ActivityNetworkErrorBinding

class NetworkErrorActivity : BaseActivity() {
  override val binding by lazy { ActivityNetworkErrorBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initListener()
  }

  private fun initListener() {
    binding.btnNetworkError.setOnClickListener {
      // TODO 재시도 기능 및 화면 전환 이벤트 구현
    }
  }
}
