package com.example.kasirgo.ui.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kasirgo.databinding.FragmentMemberBinding

class MemberFragment : Fragment() {

    private var _binding: FragmentMemberBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vouchersViewModel =
            ViewModelProvider(this).get(MemberViewModel::class.java)

        _binding = FragmentMemberBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMember
        vouchersViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}