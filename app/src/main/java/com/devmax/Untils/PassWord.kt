package com.devmax.Untils

import android.content.res.Resources
import com.devmax.profile.R

class PassWord(text:String) {

    companion object{
        fun verifyPassDifficult(text: String): Int {
            val regexLN =
                Regex("^(?=.*[a-z])(?=.*\\d).+\$") // Ao menos uma letras minuscula e um numero
            val regexUN = Regex("^(?=.*[A-Z])(?=.*\\d).+\$") // Letras maiúsculas e numeros
            val regexUL = Regex("^(?=.*[a-z])(?=.*[A-Z]).+\$") // Letras maiúsculas e minusculas
            val regexUp = Regex("^[A-Z]+$")  // Letras maiúsculas
            val regexLow = Regex("^[a-z]+$")  // Letras minusculas
            val regexNum = Regex("^[0-9]+$")  // Letras minusculas
            val regexLUNS = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).+$")

            if (text.isEmpty()) {
               return 0
            } else if (text.length < 6) {
                return 1
                //Menor que 6 caracteres é muito curta
            } else if (regexLUNS.matches(text)) {
                return  2
                //Contém ao menos um letra maiúscula, minuscula, número e caractere especial
            } else if (text.length >= 6 && (regexUp.matches(text) || regexLow.matches(text) || regexNum.matches(text))) {
                return  3
                //Só letras maiúsculas, minúsculas ou números é muito fraca
            } else if (regexUL.matches(text)) {
                return  4
                //Só letras maiúsculas e minúsculas é fraca
            } else if(regexLN.matches(text) || regexUN.matches(text)) {
                return 5
                //Só letras minúsculas e números ou letras maiúsculas e números é comum
            }
            return 9 //coloquei o n para não dar erro
        }
    }
}