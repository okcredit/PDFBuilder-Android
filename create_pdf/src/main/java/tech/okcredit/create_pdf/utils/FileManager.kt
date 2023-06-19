package tech.okcredit.create_pdf.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.Serializable
import java.net.URLConnection
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Admin on 02-01-2017.
 */
class FileManager private constructor() {
    private val tempDirectoryName = "temp"
    private val tempDuplicateFileNameSuffix = "dup"
    private val DOCUMENT_THUMBNAIL_SUFFIX = "-thumb.jpeg"
    private val IMAGE_DOCUMENT_MAX_THUMBNAIL_SIZE = 50 // 50KB

    /**
     * Write file from InputStream, file will be overwritten if exist
     *
     * @param context          context
     * @param inputStream      input stream reader of data (file)
     * @param fileName         name of file which has to saved
     * @param overWriteIfExist if true, file will be overwritten if exist
     * @return saved file
     * @throws IOException
     */
    @Throws(Exception::class, IOException::class)
    fun saveFileToPrivateStorageFromInputStream(
        context: Context,
        inputStream: InputStream,
        fileName: String,
        overWriteIfExist: Boolean,
        createThumbnail: Boolean
    ): File {
        // Create file directory if not exist
        makeDirectoryInPrivateStorage(context, fileName.substring(0, fileName.indexOf("/")))
        // Start referencing a new file
        val fileToSave = File(context.getExternalFilesDir(null), fileName)
        if (overWriteIfExist == false) {
            // Check if file already exist or not, return if exist
            val isFileAlreadyExist = hasExternalStoragePrivateFile(context, fileName)
            if (isFileAlreadyExist) {
                throw Exception("File Already Exists, make it overWritable to replace.")
            }
        }
        val fileOutput = FileOutputStream(fileToSave)
        val buffer = ByteArray(1024)
        var bufferLength = 0
        while (inputStream.read(buffer).also { bufferLength = it } > 0) {
            fileOutput.write(buffer, 0, bufferLength)
        }
        fileOutput.flush()
        fileOutput.close()
        inputStream.close()
        var fileMimeType = getMimeType(context, fileToSave)
        if (fileMimeType == null) {
            fileMimeType = ""
        }
        if (createThumbnail && fileMimeType.split("/".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0] == "image") {
            // Create thumbnail
            try {
                val thumbNailFile = File(fileToSave.absolutePath + DOCUMENT_THUMBNAIL_SUFFIX)
                if (thumbNailFile.exists() == false) {
                    // Create if not already exist
                    val thumbnailBitmap =
                        crateThumbnail(fileToSave, IMAGE_DOCUMENT_MAX_THUMBNAIL_SIZE)
                    val out: OutputStream = FileOutputStream(thumbNailFile)
                    thumbnailBitmap.compress(CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()
                }
            } catch (e: IOException) {
                // Unable to create file, likely because external storage is
                // not currently mounted.
                Log.e("ExternalStorage", "Error writing thumbnail")
                throw e
            }
        }
        return fileToSave
    }

    /**
     * Save a string in file
     *
     * @param fileToWrite
     * @param dataToWrite
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun saveStringToFile(fileToWrite: File, dataToWrite: String?): File {
        return try {
            val fw = FileWriter(fileToWrite)
            fw.write(dataToWrite)
            fw.close()
            fileToWrite
        } catch (e: IOException) {
            e.printStackTrace()
            throw Exception(e.message)
        }
    }

    /**
     * Delete file in external private storage
     *
     * @param context  context
     * @param fileName name of file in external private storage to be deleted
     * @return boolean is file deleted successfully or not
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteExternalStoragePrivateFile(context: Context, fileName: String?): Boolean {
        // Get path for the file on external storage.
        // If external storage is not currently mounted this will fail.
        val file = File(context.getExternalFilesDir(null), fileName)
        return if (file != null) {
            val fileDeleted = file.delete()
            val thumbFile = File(file.absolutePath + DOCUMENT_THUMBNAIL_SUFFIX)
            if (fileDeleted && thumbFile.exists()) {
                thumbFile.delete()
            }
            true
        } else {
            throw Exception("File does not exist.")
        }
    }

    /**
     * Check if file is available in private storage
     *
     * @param context  context
     * @param fileName Name of file in external private storage to be checked
     * @return boolean is file exist or not
     */
    fun hasExternalStoragePrivateFile(context: Context, fileName: String?): Boolean {
        // Get path for the file on external storage.
        // If external storage is not currently mounted this will fail.
        val file = File(context.getExternalFilesDir(null), fileName)
        return file?.exists() ?: false
    }

    /**
     * Get list of all files in external Storage
     *
     * @param context context
     * @return list of files
     */
    fun listExternalStoragePrivateFile(context: Context): Array<File> {
        val path = context.getExternalFilesDir(null).toString() + ""
        Log.d("Files", "Path: $path")
        val directory = File(path)
        return directory.listFiles()
    }

    /**
     * Check if file exists or not and return its url
     *
     * @param context  context
     * @param fileName name of file
     * @return path of file
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getFileUrlFromExternalStoragePrivateFile(context: Context, fileName: String): String {
        if (hasExternalStoragePrivateFile(context, fileName)) {
            return context.getExternalFilesDir(null).toString() + File.separator + fileName
        }
        throw Exception("No File Found")
    }

    /**
     * Resize image to specific size
     *
     * @param file file which has to be resized
     * @param size new size to scale to
     * @return resized image bitmap
     * @throws Exception
     */
    @Throws(Exception::class)
    fun crateThumbnail(file: File, size: Int): Bitmap {
        return try {
            // Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file.absolutePath, o)
            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= size && o.outHeight / scale / 2 >= size) scale *= 2

            // Decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            BitmapFactory.decodeFile(file.absolutePath, o2)
        } catch (e: Throwable) {
            e.printStackTrace()
            throw Exception(e.message)
        }
    }

    /**
     * Check if thumbnail file exists or not and return its url
     *
     * @param context  context
     * @param fileName name of file whose thumbnail has to be given
     * @return path of file
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getThumbnailFileUrlFromExternalStoragePrivateFile(
        context: Context,
        fileName: String
    ): String {
        if (hasExternalStoragePrivateFile(context, fileName + DOCUMENT_THUMBNAIL_SUFFIX)) {
            return context.getExternalFilesDir(null)
                .toString() + File.separator + fileName + DOCUMENT_THUMBNAIL_SUFFIX
        }
        throw Exception("No File Found")
    }

    /**
     * Get Mime type from a file
     *
     * @param file file whose mime type has to find
     * @return mime type of string
     */
    fun getMimeType(context: Context, file: File): String {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.absolutePath)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        if (type == null) {
            try {
                type = file.toURI().toURL().openConnection().contentType
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (type == null) {
            type = context.contentResolver.getType(Uri.fromFile(file))
        }
        if (type == null) {
            type = URLConnection.guessContentTypeFromName(file.name)
        }
        if (type == null) {
            // If nothing worked, just set mime type to empty string
            type = ""
        }
        return type
    }

    /**
     * Gte file's extension without "."
     *
     * @param sourceFile whole extension has to be found
     * @return file extension without "."
     */
    fun getFileExtension(sourceFile: File?): String? {
        if (sourceFile == null || sourceFile.name.lastIndexOf(".") <= 0) {
            return null
        }
        val fileNameParts =
            sourceFile.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        Log.d("AddNewDocumentActivity", "fileNameParts.length: " + fileNameParts.size)
        Log.d(
            "AddNewDocumentActivity",
            "getFileExtension: " + fileNameParts[fileNameParts.size - 1]
        )
        return fileNameParts[fileNameParts.size - 1]
    }

    fun getFileFromURI(context: Context, contentUri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(contentUri)
            val fileToSave = createTempFile(
                context,
                getFileExtension(File(getFileName(context, contentUri))),
                false
            )
            val fileOutput = FileOutputStream(fileToSave)
            val buffer = ByteArray(1024)
            var bufferLength = 0
            while (inputStream!!.read(buffer).also { bufferLength = it } > 0) {
                fileOutput.write(buffer, 0, bufferLength)
            }
            fileOutput.flush()
            fileOutput.close()
            inputStream.close()
            fileToSave
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun getFileName(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    /**
     * Generates a random path for file within 0 - 9
     *
     * @param context current context
     * @return random directory number
     * @throws SecurityException
     */
    @Throws(SecurityException::class)
    private fun getRandomFileDirectory(context: Context): Int {
        val randomDirectoryName = Utilities.generateRandomNumber(0, 9)
        makeDirectoryInPrivateStorage(context, randomDirectoryName.toString())
        return randomDirectoryName
    }

    private fun makeDirectoryInPrivateStorage(context: Context, directoryName: String): Boolean {
        val randomDirectory =
            File(context.getExternalFilesDir(null).toString() + File.separator + directoryName)
        if (!randomDirectory.exists()) {
            println("creating directory: $directoryName")
            randomDirectory.mkdir()
        }
        return true
    }

    /**
     * Get Temp folder from private storage (create one if not exist)
     *
     * @param context current context
     * @return temp folder location (path)
     */
    fun getTempFolder(context: Context): String {
        val tempDirectory =
            File(context.getExternalFilesDir(null).toString() + File.separator + tempDirectoryName)
        if (!tempDirectory.exists()) {
            println("creating directory: temp")
            tempDirectory.mkdir()
        }
        return tempDirectory.absolutePath
    }

    /**
     * Create a temporary file in temp folder
     *
     * @param context       current context
     * @param withExtension specify file extension
     * @param withDuplicate create a duplicate of temp file (used in case of image processing), DUPLICATE FILES ARE JUST A EMPTY FILE PATH, ALL FILE MANAGEMENT HAS TO BE DONE BY YOU.
     * @return created temp file
     */
    fun createTempFile(context: Context, withExtension: String?, withDuplicate: Boolean): File {
        // Actual temp file
        var tempFileName = java.lang.Long.toString(Date().time)
        if (withExtension != null && withExtension.isEmpty() == false) {
            tempFileName = "$tempFileName.$withExtension"
        }
        val tempFile = File(getTempFolder(context), tempFileName)
        if (withDuplicate) {
            // Duplicate of temp file
            val tempDuplicateFile =
                File(getTempFolder(context), tempFileName + tempDuplicateFileNameSuffix)
        }
        return tempFile
    }

    /**
     * Create a temporary file in temp folder with user given name, file will be overwritten if name is collapsed
     *
     * @param context       current context
     * @param withDuplicate create a duplicate of temp file (used in case of image processing), DUPLICATE FILES ARE JUST A EMPTY FILE PATH, ALL FILE MANAGEMENT HAS TO BE DONE BY YOU.
     * @return created temp file
     */
    fun createTempFileWithName(
        context: Context,
        tempFileName: String,
        withDuplicate: Boolean
    ): File {
        // Actual temp file
        val tempFile = File(getTempFolder(context), tempFileName)
        if (withDuplicate) {
            // Duplicate of temp file
            val tempDuplicateFile =
                File(getTempFolder(context), tempFileName + tempDuplicateFileNameSuffix)
        }
        return tempFile
    }

    /**
     * Save a bitmap to a file
     *
     * @param fileToSave      File object to save file to
     * @param bitmapToSave    Bitmap to be saved
     * @param createThumbnail true if you want to create thumbnail as well
     * @return saved file
     * @throws Exception
     * @throws IOException
     */
    @Throws(Exception::class, IOException::class)
    fun saveImageToFile(
        fileToSave: File,
        bitmapToSave: Bitmap,
        compressFormat: CompressFormat?,
        createThumbnail: Boolean
    ): File {
        try {
            // If external storage is not currently mounted this will fail.
            val out: OutputStream = FileOutputStream(fileToSave)
            bitmapToSave.compress(compressFormat, 100, out)
            out.flush()
            out.close()
        } catch (e: IOException) {
            // Unable to create file, likely because external storage is
            // not currently mounted.
            Log.e("ExternalStorage", "Error writing $fileToSave", e)
            throw IOException("Error writing " + fileToSave + ", Exception: " + e.message)
        }
        if (createThumbnail) {
            try {
                val thumbNailFile = File(fileToSave.absolutePath + DOCUMENT_THUMBNAIL_SUFFIX)
                val thumbnailBitmap = crateThumbnail(fileToSave, IMAGE_DOCUMENT_MAX_THUMBNAIL_SIZE)
                val out: OutputStream = FileOutputStream(thumbNailFile)
                thumbnailBitmap.compress(compressFormat, 100, out)
                out.flush()
                out.close()
            } catch (e: IOException) {
                // Unable to create file, likely because external storage is
                // not currently mounted.
                Log.e("ExternalStorage", "Error writing thumbnail")
                throw e
            }
        }
        return fileToSave
    }

    /**
     * Get temporary file by its name
     *
     * @param context  current context
     * @param fileName name if temp file
     * @return file if found, null if not
     */
    @Throws(Exception::class)
    fun getTempFile(context: Context, fileName: String?): File {
        val tempFile = File(getTempFolder(context), fileName)
        if (tempFile.exists() == false) {
            throw Exception("File not found")
        }
        return tempFile
    }

    /**
     * Get duplicate of temporary file by its temp file name
     *
     * @param context  current context
     * @param fileName name if temp file
     * @return file if found, null if not
     */
    @Throws(Exception::class)
    fun getTempDuplicateFile(context: Context, fileName: String): File {
        val tempDuplicateFile = File(getTempFolder(context), fileName + tempDuplicateFileNameSuffix)
        if (tempDuplicateFile.exists() == false) {
            throw Exception("File not found")
        }
        return tempDuplicateFile
    }

    /**
     * Delete all files from temp directory
     *
     * @param context current context
     */
    fun cleanTempFolder(context: Context) {
        val tempDirectory =
            File(context.getExternalFilesDir(null).toString() + File.separator + tempDirectoryName)
        if (tempDirectory.exists()) {
            try {
                for (f in tempDirectory.listFiles()) f.delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Get human readable file size
     *
     * @param fileSizeInKb size of file in kb
     * @return human readable file
     */
    fun getReadableFileSize(fileSizeInKb: Long): String {
        val numberFormat: NumberFormat = DecimalFormat("##.##")
        return if (fileSizeInKb < 1024) {
            String.format(
                Locale.getDefault(),
                "%s KB",
                numberFormat.format(fileSizeInKb.toFloat().toDouble())
            )
        } else if (fileSizeInKb >= 1024 && fileSizeInKb < 1024 * 1024) {
            String.format(
                Locale.getDefault(),
                "%s MB",
                numberFormat.format((fileSizeInKb.toFloat() / 1024).toDouble())
            )
        } else {
            String.format(
                Locale.getDefault(),
                "%s GB",
                numberFormat.format((fileSizeInKb.toFloat() / (1024 * 1024)).toDouble())
            )
        }
    }

    enum class FILE_TYPE : Serializable {
        IMAGE, AUDIO, VIDEO, PDF, TEXT, DOC, PPT, XLS, UNKNOWN
    }

    enum class IMAGE_QUALITY(val size: Int) : Serializable {
        HIGH(1024), MID(512), LOW(256)

    }

    companion object {
        var instance: FileManager? = null
            get() {
                if (field == null) {
                    field = FileManager()
                }
                return field
            }
            private set
    }
}