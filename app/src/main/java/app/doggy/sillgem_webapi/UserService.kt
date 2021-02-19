package app.doggy.sillgem_webapi

import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    /*
    * ユーザー情報を取得するメソッド。
    * GitHubのユーザー名を引数にとって、取得したユーザー情報であるUserクラスを返す。
    */

    //GET：HTTPリクエストの一つ。サーバーから情報を取得する。
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User

}