package com.devmax.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.devmax.Untils.PassWord
import com.devmax.profile.R

class PassDifficult : Fragment() {
    private lateinit var textViewDifficult: TextView;
    lateinit var passInput: EditText;
    lateinit var text: Editable;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pass_difficult, container, false)

        passInput = view.findViewById<EditText>(R.id.passInput)
        textViewDifficult = view.findViewById<EditText>(R.id.textViewDifficult)

        // Pegando o texto do fragmento e guardando da var TEXT
        passInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                if (s != null) {
                    text = s

                }

                val result = PassWord.verifyPassDifficult(text.toString())

                if (result == 0) {
                    textViewDifficult.text = resources.getString(R.string.empty_pass)
                } else if (result == 1) {
                    textViewDifficult.text = resources.getString(R.string.very_short_pass)
                } else if (result == 2) {
                    textViewDifficult.text = resources.getString(R.string.strong_pass)
                } else if (result == 3) {
                    textViewDifficult.text = resources.getString(R.string.very_weake_pass)
                } else if (result == 4) {
                    textViewDifficult.text = resources.getString(R.string.weake_pass)
                } else if (result == 5) {
                    textViewDifficult.text = resources.getString(R.string.commom_pass)
                }

            }
        })
        return view
    }
//  companion object{
//      fun feedBackPassDifficult(senha: EditText): Int {
//
//          senha.addTextChangedListener(object : TextWatcher {
//              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//              override fun afterTextChanged(s: Editable?) {
//
//                  if (s != null) {
//                      text = s
//
//                  }
//
//                  val result = PassWord.verifyPassDifficult(text.toString())
//
//                  if (result == 0) {
//                      textViewDifficult.text = resources.getString(R.string.empty_pass)
//                  } else if (result == 1) {
//                      textViewDifficult.text = resources.getString(R.string.very_short_pass)
//                  } else if (result == 2) {
//                      textViewDifficult.text = resources.getString(R.string.strong_pass)
//                  } else if (result == 3) {
//                      textViewDifficult.text = resources.getString(R.string.very_weake_pass)
//                  } else if (result == 4) {
//                      textViewDifficult.text = resources.getString(R.string.weake_pass)
//                  } else if (result == 5) {
//                      textViewDifficult.text = resources.getString(R.string.commom_pass)
//                  }
//
//              }
//          })
//
//
//          return result
//      }
  }



