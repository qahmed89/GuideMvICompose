package com.example.myapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.crypto.Cipher
import javax.xml.bind.DatatypeConverter
import kotlin.time.Duration.Companion.days

class AsymmetricEncryption() {

    private val keyGen: KeyPairGenerator = KeyPairGenerator.getInstance(DEFAULT_ENCRYPTION_ALGORITHM)
    private val keyPair: KeyPair

    init {
        keyGen.initialize(DEFAULT_ENCRYPTION_KEY_LENGTH)
        keyPair = keyGen.generateKeyPair()
    }

    companion object {
        fun convertToBase64String(key: ByteArray): String {
            return DatatypeConverter.printBase64Binary(key).replace("(.{64})".toRegex(), "$1\n")}
        private const val DEFAULT_ENCRYPTION_ALGORITHM = "RSA"
        private const val DEFAULT_ENCRYPTION_KEY_LENGTH = 1024
        private const val DEFAULT_ALGORITHM = "RSA/ECB/PKCS1Padding"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val KEY_ALIAS = "Keyalaisras"
        fun hasMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        @Throws(Exception::class)
        fun decrypt(privateKey: ByteArray, data: String): String {
            val cipher: Cipher = Cipher.getInstance(DEFAULT_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance(DEFAULT_ENCRYPTION_ALGORITHM).generatePrivate(
                PKCS8EncodedKeySpec(privateKey)
            ))
            return String(cipher.doFinal(DatatypeConverter.parseBase64Binary(data)), Charsets.UTF_8)
        }

        @Throws(Exception::class)
        fun encrypt(publicKeyEncoded: String, data: String): String {
            var cipher: Cipher = Cipher.getInstance(DEFAULT_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance(DEFAULT_ENCRYPTION_ALGORITHM).generatePublic(
                X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(publicKeyEncoded))
            ))
            return convertToBase64String(cipher.doFinal(data.toByteArray()))
        }

    }

    fun fetchPrivateKey(): ByteArray {
        return this.keyPair.private.encoded
    }

    fun fetchPublicKey(): ByteArray {
        return this.keyPair.public.encoded
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val dateString = "01/02/2024"
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val date = format.parse(dateString)



//   val publicKey=  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOo26aa/6lphog896AqENhY15SnVXhgoPY1EJb 2uzNuOOE/ddFkKcwwBGFiaFhEqb/rP8OzTSrqD5kaUAyxrisi/z4SoXtfn3jFrcHP6CgTYxEX7oOyGpuaZPQ2IMjed8MTJWUg4BrHGr7asZz2hmhVBGLFOhGGqvdq7K8beAkZQIDAQAB"
//    val privateKey = ""
//    val x = AsymmetricEncryption()
//   val  encrptedData = AsymmetricEncryption.encrypt(publicKey,"Hello, World!")|


}