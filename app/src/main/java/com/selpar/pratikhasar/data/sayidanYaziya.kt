package com.selpar.pratikhasar.data

import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

class sayidanYaziya{
    private val tensNames = arrayOf(
        "",
        "On ",
        "Yirmi ",
        "Otuz ",
        "Kırk ",
        "Elli ",
        "Altmış ",
        "Yetmiş ",
        "Seksen ",
        "Doksan "
    )
    private val numNames = arrayOf(
        "", "Bir", "İki", "Üç", "Dört", "Beş", "Altı", "Yedi", "Sekiz", "Dokuz" ,"On",
        " Onbir",
        " Oniki",
        " Onüç",
        " Ondört",
        " Onbeş",
        " Onaltı",
        " Onyedi",
        " Onsekiz",
        " Ondokuz"
    )

    fun convertLessThanOneThousand(number: Int): String {
        var number = number
        var soFar: String
        if (number % 100 < 20) {
            soFar = numNames[number % 100]
            number /= 100
        } else {
            soFar = numNames[number % 10]
            number /= 10
            soFar = tensNames[number % 10] + soFar
            number /= 10
        }
        return if (number == 0) soFar else numNames[number] + " Yüz " + soFar
    }

    fun convert(number: Long): String {
        // 0 to 999 999 999 999
        if (number == 0L) {
            return "Sıfır"
        }
        var snumber = java.lang.Long.toString(number)

        // pad with "0"
        val mask = "000000000000"
        val df = DecimalFormat(mask)
        snumber = df.format(number)

        // XXXnnnnnnnnn
        val billions = snumber.substring(0, 3).toInt()
        // nnnXXXnnnnnn
        val millions = snumber.substring(3, 6).toInt()
        // nnnnnnXXXnnn
        val hundredThousands = snumber.substring(6, 9).toInt()
        // nnnnnnnnnXXX
        val thousands = snumber.substring(9, 12).toInt()
        val tradBillions: String
        tradBillions = when (billions) {
            0 -> ""
            1 -> (convertLessThanOneThousand(billions)
                    + " Milyar ")
            else -> (convertLessThanOneThousand(billions)
                    + " Milyar ")
        }
        var result = tradBillions
        val tradMillions: String
        tradMillions = when (millions) {
            0 -> ""
            1 -> (convertLessThanOneThousand(millions)
                    + " Milyon ")
            else -> (convertLessThanOneThousand(millions)
                    + " Milyon ")
        }
        result = result + tradMillions
        val tradHundredThousands: String
        tradHundredThousands = when (hundredThousands) {
            0 -> ""
            1 -> " Bin "
            else -> (convertLessThanOneThousand(hundredThousands)
                    + " Bin ")
        }
        result = result + tradHundredThousands
        val tradThousand: String
        tradThousand = convertLessThanOneThousand(thousands)
        result = result + tradThousand

        // remove extra spaces!
        return result.replace("^\\s+".toRegex(), "").replace("\\b\\s{2,}\\b".toRegex(), " ")
    }
}