package gsc.ZupStar.NetWork.Response

data class DefaultResponse<T>(
    val success: Boolean,
    val message: String,
    val code: Int,
    val data: T
)
