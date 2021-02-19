package app.doggy.sillgem_webapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.api.load
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RetrofitとGSONを組み合わせて通信の準備をする。
        val gson: Gson =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val userService: UserService = retrofit.create(UserService::class.java)

        requestButton.setOnClickListener {

            /*
            * メインスレッド
            * 画面を更新する担当
            * 順番に処理される
            * 重たい処理をすると画面の更新が止まる
            * 5秒以上止めてはいけない
            */

            /*
            * 別スレッド
            * 重たい処理を実行する
            * メインスレッドを止めない
            * 画面の更新を行うことはできない
            */

            //runBlocking内
            runBlocking(Dispatchers.IO) {
                runCatching {
                    userService.getUser(userIdEditText.text.toString())
                }
            }.onSuccess {
                //成功した時（runBlocking外）
                avatarImageView.load(it.avatarUrl)
                nameTextView.text = it.name
                userIdTextView.text = it.userId
                followingTextView.text = it.following.toString()
                followersTextView.text = it.followers.toString()
            }.onFailure {
                //失敗した時（runBlocking外）
                Toast.makeText(applicationContext, "失敗", Toast.LENGTH_SHORT).show()
            }

        }
    }
}