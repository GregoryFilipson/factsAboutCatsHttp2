import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ClientHttp {
    public static void main(String[] args) {
        final ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        HttpGet request = new HttpGet("https://cat-fact.herokuapp.com/facts");
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            Person response1 = mapper.readValue(body, new TypeReference<>() {
            });
            Stream<Person.All> stream = response1.getAll().stream();
            stream.filter(value ->  value.getUpvotes() != null & value.getUpvotes() > 0)
                    .forEach(System.out::println);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public class Person {
        @JsonProperty("all")
        public List<All> all;
        public List<All> getAll() {
            return all;
        }

        static public class All {
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
    }

    static public class User {
        @JsonProperty("_id")
        public String id;
        @JsonProperty("name")
        public Name name;
        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", name=" + name +
                    '}';
        }
    }

    static public class Name {
        @JsonProperty("first")
        public String first;
        @JsonProperty("last")
        public String last;
        @Override
        public String toString() {
            return "Name{" +
                    "first='" + first + '\'' +
                    ", last='" + last + '\'' +
                    '}';
        }
    }
}