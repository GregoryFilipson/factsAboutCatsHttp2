import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "type",
        "text",
        "user",
        "upvotes",
        "userUpvoted"
})

public class All {
    @JsonProperty("_id")
    public String id;
    @JsonProperty("type")
    public String type;
    @JsonProperty("text")
    public String text;
    @JsonProperty("user")
    public User user;
    @JsonProperty("upvotes")
    public Integer upvotes;
    @JsonProperty("userUpvoted")
    public Object userUpvoted;
    public Integer getUpvotes() {
        return upvotes;
    }
    @Override

    public String toString() {
        return "All{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", upvotes=" + upvotes +
                ", userUpvoted=" + userUpvoted +
                '}';
    }
}