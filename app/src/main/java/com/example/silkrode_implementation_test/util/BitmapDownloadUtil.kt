package com.example.silkrode_implementation_test.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.webkit.URLUtil
import kotlinx.coroutines.*
import java.io.*
import java.net.HttpURLConnection
import java.net.NoRouteToHostException
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLException

class BitmapDownloadUtil {

    interface OnPicReady {
        fun ready(bitmap: Bitmap)
        fun fail(e: Exception)
    }

    companion object {
        private val dTag by lazy { BitmapDownloadUtil::class.java.simpleName }

        fun downloadPic(
            url: String,
            listener: OnPicReady
        ) {
            CoroutineScope(Job() + Dispatchers.Default).apply {
                launch(Dispatchers.Default) {
                    if (!URLUtil.isNetworkUrl(url)) {
                        listener.fail(Exception("Url is not a network url!"))
                        return@launch
                    }
                    val imgUrl = URL(url)
                    val urlConnection = imgUrl.openConnection()
                    var inputStream: InputStream? = null
                    try {
                        inputStream = if (URLUtil.isHttpsUrl(url)) {
                            (urlConnection as HttpsURLConnection).inputStream
                        } else {
                            (urlConnection as HttpURLConnection).inputStream
                        }
                    } catch (e: FileNotFoundException) {
                        LogUtil.e(dTag, "downloadPic fail!! FileNotFoundException, $e, url:$url")
                        listener.fail(e)
                    } catch (e: NoRouteToHostException) {
                        LogUtil.e(dTag, "downloadPic fail!! NoRouteToHostException, $e, url:$url")
                        listener.fail(e)
                    } catch (e: IOException) {
                        LogUtil.e(dTag, "downloadPic fail!! IOException, $e, url:$url")
                        listener.fail(e)
                    }
                    inputStream?.let { it0 ->
                        val clone = ByteArrayOutputStream()
                        val buffer = ByteArray(1024)
                        var len: Int = inputStream.read(buffer)
                        try {
                            while (len > -1) {
                                clone.write(buffer, 0, len)
                                len = it0.read(buffer)
                            }
                        } catch (e: SSLException) {
                            LogUtil.e(dTag, e.toString())
                            listener.fail(e)
                            return@launch
                        } finally {
                            clone.flush()
                        }
                        val options = BitmapFactory.Options()
                        options.inPreferredConfig = Bitmap.Config.RGB_565
                        options.inJustDecodeBounds = false
                        val bitmap = BitmapFactory.decodeStream(
                            ByteArrayInputStream(clone.toByteArray()),
                            null,
                            options
                        )!!
                        listener.ready(bitmap)
                    }
                }
            }
        }
    }
}