package com.fasolutions.finance

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

object ResourceLoader {
    fun getAllCompanyCodesFile() = File(getUrlFile("companies-all.txt").file)
    fun getEvaluationsFile() = File(getUrlFile("evaluations.csv").file)
    private fun getUrlFile(fileName: String) = this.javaClass.getResource("/$fileName")!!

    fun loadFileAsLines(file: File): List<String> {
        val lines = mutableListOf<String>()
        try {
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    lines.add(line!!)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return lines
    }
}