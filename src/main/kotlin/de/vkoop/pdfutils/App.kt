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
class PdfMerge :  Runnable{

    @CommandLine.Parameters(index = "0")
    lateinit var file1 : String;

    @CommandLine.Option(names = ["-r1"], description = ["first file should be reversed"])
    var reverseFile1 =  false

    @CommandLine.Parameters(index = "1")
    lateinit var file2 : String;

    @CommandLine.Option(names = ["-r2"], description = ["second file should be reversed"])
    var reverseFile2 =  false

    @CommandLine.Option(names = ["-m"], description = ["INTERLEAVE / CONCAT"])
    var mergeMode = MergeMode.CONCAT;

    @CommandLine.Option(names = ["-o"], required = true)
    lateinit var outFile : String;

    fun  concat(){
        val pdfReader1 = PdfReader(file1)
        val pdfReader2 = PdfReader(file2)

        val outDocument = Document()
        val smartCopy = PdfSmartCopy(outDocument, FileOutputStream(outFile))
        outDocument.open()

        for(i in 1 .. pdfReader1.numberOfPages){
            smartCopy.addPage(smartCopy.getImportedPage(pdfReader1, i))
        }

        for(i in 1 .. pdfReader2.numberOfPages){
            smartCopy.addPage(smartCopy.getImportedPage(pdfReader2, i))
        }

        pdfReader1.close()
        pdfReader2.close()
        smartCopy.close()
    }

    fun interleave(){
        val pdfReader1 = PdfReader(file1)
        val pdfReader2 = PdfReader(file2)

        val numberOfPages1 = pdfReader1.numberOfPages
        val numberOfPages2 = pdfReader2.numberOfPages

        val outDocument = Document()
        val smartCopy = PdfSmartCopy(outDocument, FileOutputStream(outFile))

        outDocument.open()

        var finished = false
        var i = 1
        while (!finished) {
            val nextPage1 = if(reverseFile1) (numberOfPages1 - (i-1)) else i
            val nextPage2 = if(reverseFile2) (numberOfPages2 - (i-1)) else i

            smartCopy.addPage(smartCopy.getImportedPage(pdfReader1,  nextPage1))
            smartCopy.addPage(smartCopy.getImportedPage(pdfReader2, nextPage2))

            i++
            if(i > numberOfPages1 || i > numberOfPages2){
                finished = true
            }
        }

        pdfReader1.close()
        pdfReader2.close()
        smartCopy.close()
    }

    override fun run(){
        if(mergeMode == MergeMode.CONCAT){
            concat();
        } else if(mergeMode == MergeMode.INTERLEAVE) {
            interleave()
        } else {
            println("Unknown mergen mode.")
        }
    }
}