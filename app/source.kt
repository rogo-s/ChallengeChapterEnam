
import com.google.gson.annotations.SerializedName

data class source(
    @SerializedName("client")
    val client: List<Client>,
    @SerializedName("configuration_version")
    val configurationVersion: String,
    @SerializedName("project_info")
    val projectInfo: ProjectInfo
)