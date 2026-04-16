package feature.home.domain.model

data class HomeMovie(
    val id: String,
    val name: String,
    val thumbUrl: String?,
    val year: Int?,
    val quality: String?,
    val lang: String?,
    val episodeCurrent: String?,
    val rating: Float? = null,
)
