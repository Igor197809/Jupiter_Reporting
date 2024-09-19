// MainActivity.kt
package com.example.jupiter_reporting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes

import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var credential: GoogleAccountCredential
    private lateinit var sheetsService: Sheets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Убедитесь, что у вас есть соответствующий макет
        setContentView(R.layout.activity_main)

        // Настройка параметров Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .build()

        // Создание клиента Google Sign-In
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Проверка, выполнен ли вход
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            initializeSheetsApi(account)
        } else {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            // Вход выполнен успешно
            initializeSheetsApi(account)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            // Обработка ошибки
        }
    }

    private fun initializeSheetsApi(account: GoogleSignInAccount) {
        // Создание учетных данных
        credential = GoogleAccountCredential.usingOAuth2(
            applicationContext, listOf(SheetsScopes.SPREADSHEETS)
        )
        credential.selectedAccount = account.account

        // Инициализация службы Sheets API
        sheetsService = Sheets.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("Jupiter_Reporting")
            .build()

        // Теперь вы можете использовать sheetsService для взаимодействия с Google Sheets API
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 1000
    }
}
