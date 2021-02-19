package app.doggy.sillgem_webapi

//@SerializedName：好きな変数名を付けられる。
import com.google.gson.annotations.SerializedName

//Data Class：複数の異なるデータ型の変数を一つにまとめたもの。
data class User(
    val name: String,
    //""の中に、使いたいJSONのパラメータを入力する。
    @SerializedName("login") val userId: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val following: Int,
    val followers: Int
)
