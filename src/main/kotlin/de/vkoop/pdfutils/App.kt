package de.vkoop.pdfutils

import com.lowagie.text.Document
import com.lowagie.text.pdf.PdfReader
import com.lowagie.text.pdf.PdfSmartCopy
import picocli.CommandLine.*
import java.io.FileOutputStream

fun main(args: Array<String>) {
    run(PdfMerge(), System.out, *args)
}

enum class MergeMode {
    INTERLEAVE, CONCAT
}

@Command
class PdfMerge : Runnable {

    @Parameters(index = "0")
    lateinit var file1: String

    @Option(names = ["-r1"], description = ["first file should be reversed"])
    var reverseFile1 = false

    @Parameters(index = "1")
    lateinit var file2: String

    @Option(names = ["-r2"], description = ["second file should be reversed"])
    var reverseFile2 = false

    @Option(names = ["-m"], description = ["INTERLEAVE / CONCAT"])
    var mergeMode = MergeMode.CONCAT

    @Option(names = ["-o"], required = true)
    lateinit var outFile: String

    private fun combine(combineFun : (List<Pair<Int,PdfReader>>, List<Pair<Int,PdfReader>>) -> List<Pair<Int,PdfReader>>) {
        val pageOrderList = combineFun(createPageReaderList(file1, reverseFile1), createPageReaderList(file2, reverseFile2))

        val outDocument = Document()
        val smartCopy = PdfSmartCopy(outDocument, FileOutputStream(outFile))
        smartCopy.compressionLevel = 9

        outDocument.open()

        pageOrderList.map { (page, reader) -> smartCopy.getImportedPage(reader, page) }
                .forEach (smartCopy::addPage)

        smartCopy.close()
    }

    private fun createPageReaderList(fileName: String, reversed: Boolean): List<Pair<Int, PdfReader>> {
        val pdfReader = PdfReader(fileName)
        val numberOfPages = pdfReader.numberOfPages
        val range = if (reversed) (numberOfPages downTo 1) else (1..numberOfPages)
        return range.map { Pair(it, pdfReader) }
    }

    override fun run() {
        when (mergeMode) {
            MergeMode.CONCAT -> combine { l1, l2 -> l1 + l2 }
            MergeMode.INTERLEAVE -> combine { l1, l2 -> l1.zip(l2).flatMap { it.toList() } }
        }
    }
}
