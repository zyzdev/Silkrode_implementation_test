package com.example.silkrode_implementation_test.util

import android.util.Log
import com.example.silkrode_implementation_test.BuildConfig

class LogUtil {

    companion object {
        private val switch = true && BuildConfig.DEBUG
        private val debugE = true && switch
        private val debugD = true && switch
        private val debugV = true && switch
        fun e(tag: String, log: String?, show: Boolean? = true) {
            if (debugE && show!!) e(tag = tag, log = log, tr = null)
        }

        fun e(tag: String, log: String?, tr: Throwable? = null, show: Boolean? = true) {
            if (debugE && show!!) Log.e(tag, log, tr)
        }

        fun w(tag: String, log: String?, show: Boolean? = true) {
            if (debugD && show!!) w(tag = tag, log = log, tr = null)
        }

        fun w(tag: String, log: String?, tr: Throwable? = null, show: Boolean? = true) {
            if (debugD && show!!) Log.w(tag, log, tr)
        }

        fun d(show: Boolean? = true, tag: String, log: String?) {
            if (debugD && show!!) d(tag = tag, log = log, tr = null)
        }

        fun d(tag: String, log: String?, tr: Throwable? = null, show: Boolean? = true) {
            if (debugD && show!!) Log.d(tag, log, tr)
        }

        fun v(tag: String, log: String?, show: Boolean? = true) {
            if (debugV && show!!) v(tag = tag, log = log, tr = null)
        }

        fun v(tag: String, log: String?, tr: Throwable? = null, show: Boolean? = true) {
            if (debugV && show!!) Log.v(tag, log, tr)
        }
    }
}