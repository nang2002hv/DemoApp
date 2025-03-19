package com.example.demoandroidapp.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.viewbinding.ViewBinding
import com.example.demoandroidapp.persentation.search.photos.SearchViewModel

abstract class BaseFragment : Fragment() {
  private val logTag by lazy(LazyThreadSafetyMode.NONE) {
    this::class.java.simpleName
  }

  @CallSuper
  override fun onAttach(context: Context) {
    super.onAttach(context)
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  @CallSuper
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  @CallSuper
  override fun onStart() {
    super.onStart()
  }

  @CallSuper
  override fun onResume() {
    super.onResume()
  }

  @CallSuper
  override fun onPause() {
    super.onPause()
  }

  @CallSuper
  override fun onStop() {
    super.onStop()
  }

  @CallSuper
  override fun onDestroyView() {
    super.onDestroyView()
  }

  @CallSuper
  override fun onDestroy() {
    super.onDestroy()
  }

  @CallSuper
  override fun onDetach() {
    super.onDetach()
  }
}

abstract class BaseFragmentWithViewBinding<VB : ViewBinding>(
  private val inflateViewBinding: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> VB
) : BaseFragment() {
  private var _binding: VB? = null

  /**
   * Valid only after [onViewCreated] and before [onDestroyView].
   */
  protected val binding: VB get() = _binding!!

  final override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = inflateViewBinding(inflater, container, false)
    return binding.root
  }

  @CallSuper
  override fun onDestroyView() {
    _binding = null // Avoid memory leaks
    super.onDestroyView()
  }

}
