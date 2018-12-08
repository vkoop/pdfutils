package de.vkoop.pdfutils

import com.lowagie.text.Document
import com.lowagie.text.pdf.PdfReader
import com.lowagie.text.pdf.PdfSmartCopy
import picocli.CommandLine
import java.io.FileOutputStream

fun main(args: Array<String>) {
    CommandLine.run(PdfMerge(), System.out, *args)
}

enum class MergeMode {
    INTERLEAVE, CONCAT
}

@CommandLine.Command
class PdfMerge : Runnable {

    @CommandLine.Parameters(index = "0")
    lateinit var file1: String

    @CommandLine.Option(names = ["-r1"], description = ["first file should be reversed"])
    var reverseFile1 = false

    @CommandLine.Parameters(index = "1")
    lateinit var file2: String

    @CommandLine.Option(names = ["-r2"], description = ["second file should be reversed"])
    var reverseFile2 = false

    @CommandLine.Option(names = ["-m"], description = ["INTERLEAVE / CONCAT"])
    var mergeMode = MergeMode.CONCAT

    @CommandLine.Option(names = ["-o"], required = true)
    lateinit var outFile: String

    fun combine(combineFun : (List<Pair<Int,PdfReader>>, List<Pair<Int,PdfReader>>) -> List<Pair<Int,PdfReader>>) {
        val pageOrderList = combineFun(createPageReaderList(file1, reverseFile1), createPageReaderList(file2, reverseFile2))

        val outDocument = Document()
        val smartCopy = PdfSmartCopy(outDocument, FileOutputStream(outFile))

        outDocument.open()

        pageOrderList.map { (page, reader) -> smartCopy.getImportedPage(reader, page) }
                .forEach { smartCopy.addPage(it) }

        smartCopy.close()
    }

    private fun createPageReaderList(fileName: String, reversed: Boolean): List<Pair<Int, PdfReader>> {
        val pdfReader = PdfReader(fileName)
        val numberOfPages = pdfReader.numberOfPages
        val range = if (reversed) (numberOfPages downTo 1) else (1..numberOfPages)
        return range.map { Pair(it, pdfReader) }
    }

    override fun run() {
        if (mergeMode == MergeMode.CONCAT) {
            combine { l1, l2 -> l1 + l2 }
        } else if (mergeMode == MergeMode.INTERLEAVE) {
            combine { l1, l2 -> l1.zip(l2).flatMap { it.toList() } }
        } else {
            println("Unknown mergen mode.")
        }
    }
}
