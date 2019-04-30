import org.gradle.api.Project
import java.io.File
import java.util.*

class KotlinBundler(project: Project) {
    val basePackage = project.group.toString()

    fun bundleToFile(partialInputPath: String, partialOutputPath: String = partialInputPath) {
        val inputPath = "src/main/kotlin/" + basePackage.replace('.', '/') + "/" + partialInputPath
        val outputPath = "build/bundled/$partialOutputPath"
        bundleToFile(File(inputPath), File(outputPath))
    }

    fun bundleToFile(inputFile: File, outputFile: File) {
        val code = bundle(inputFile)

        if (!outputFile.exists()) {
            outputFile.parentFile.mkdirs()
            outputFile.createNewFile()
        }

        outputFile.writeText(code)

        println("Bundled ${inputFile.path} to ${outputFile.path}")
    }

    fun bundle(entryFile: File): String {
        val processedFiles = mutableSetOf<File>()

        val imports = mutableSetOf<String>()
        val code = mutableListOf<String>()

        val fileQueue = ArrayDeque<File>()
        fileQueue.add(entryFile.parentFile)

        while (fileQueue.isNotEmpty()) {
            val file = fileQueue.poll()

            if (file.isFile) {
                if (processedFiles.add(file)) {
                    processFile(file, fileQueue, imports, code)
                }
            } else {
                processDirectory(file, fileQueue)
            }
        }

        val codeLines = code.joinToString("\n\n")

        return if (imports.isNotEmpty()) {
            val importLines = imports.joinToString("\n")
            importLines + "\n\n" + codeLines
        } else {
            codeLines
        }
    }

    fun processFile(file: File, fileQueue: Queue<File>, imports: MutableSet<String>, code: MutableList<String>) {
        val fileImports = arrayListOf<String>()
        val fileCode = arrayListOf<String>()

        file.forEachLine {
            when {
                it.startsWith("package") -> return@forEachLine
                it.startsWith("import ") -> fileImports.add(it)
                else -> fileCode.add(it)
            }
        }

        fileImports.forEach {
            val import = it.substring(7)

            if (import.startsWith(basePackage)) {
                val path = "src/main/kotlin/" + import.replace('.', '/')
                fileQueue.add(File(path.substringBeforeLast('/')))
            } else {
                imports.add(it)
            }
        }

        code.add(fileCode.joinToString("\n").trim())
    }

    fun processDirectory(directory: File, fileQueue: Queue<File>) {
        directory.listFiles().forEach {
            if (it.isFile && it.extension == "kt") {
                fileQueue.add(it)
            }
        }
    }
}
