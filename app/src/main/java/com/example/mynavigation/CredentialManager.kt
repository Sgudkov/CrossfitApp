package com.example.mynavigation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}

data class UserLoginDetailModel(
    @SerializedName("access_token")
    var accesstoken: String? = "",

    )

class GlobalLoginData : ViewModel() {
    private var userLoginDetailModel by mutableStateOf(UserLoginDetailModel())

    fun setToken(newToken: String) {
        userLoginDetailModel = userLoginDetailModel.copy(accesstoken = newToken)
    }

    fun getToken(): String? {
        return userLoginDetailModel.accesstoken
    }
}

data class UserEmailVerifyModel(
    @SerializedName("email_verify")
    var emailVerify: String? = "",

    )

var globalLoginData = GlobalLoginData()

private const val BASE_URL =
    "http://10.0.2.2:80"

var gson = GsonBuilder()
    .setLenient()
    .create()

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    @Headers(
        "Content-Type: application/json",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate, br",
        "Connection: keep-alive"
    )
    @POST("register")
    @JvmSuppressWildcards
    suspend fun postRegister(@Body body: Map<String, String>): Response<String>

    @POST("login")
    @JvmSuppressWildcards
    suspend fun postLogin(@Body body: Map<String, String>): UserLoginDetailModel

    @POST("emailverify")
    @JvmSuppressWildcards
    suspend fun postEmailVerify(@Body body: Map<String, String>): Response<String>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

}


