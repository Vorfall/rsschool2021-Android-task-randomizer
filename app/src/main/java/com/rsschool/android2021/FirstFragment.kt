package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: FragmentChanger? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val min = view.findViewById<EditText>(R.id.min_value)

        val max = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            val startNum = min.text.toString()
            val finalNum = max.text.toString()
            var errorMessage: String? = ""
            if (startNum.toInt() > finalNum.toInt()){
                errorMessage = "Максимальное значение должно быть больше минимального"
            }
            if (startNum.isEmpty() || finalNum.isEmpty()){
                errorMessage = "Не все поля заполнены"
            }
            if (startNum.toLong() > Int.MAX_VALUE || finalNum.toLong() > Int.MAX_VALUE){
                errorMessage = "Превышен диапазон значений"
            }
            if (errorMessage.isNullOrEmpty()){
                listener?.openSecondFragment(startNum.toInt(), finalNum.toInt())
            } else Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        activity?.onBackPressedDispatcher?.addCallback() {
            activity?.finish()
        }
    }




    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentChanger
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}