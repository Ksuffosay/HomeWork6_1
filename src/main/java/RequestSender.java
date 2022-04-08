import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.SQLOutput;

public class RequestSender {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_KEY = "I4PfS6pRjHfLN4O9DW4bNXX7O7d0in2o";

    public static MinMaxTemp getTempMinAndMaxTemp(String cityId) throws IOException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("5day")
                .addPathSegment(cityId)
                .addQueryParameter("apikey", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String json = response.body().string();

        String maxTemp = null;
        String minTemp = null;
        try {
            maxTemp = objectMapper.readTree(json)
                    .at("/DailyForecasts")
                    .get(0)
                    .at("/Temperature/Maximum/Value")
                    .asText();
            minTemp = objectMapper.readTree(json)
                    .at("/DailyForecasts")
                    .get(0)
                    .at("/Temperature/Minimum/Value")
                    .asText();
        } catch (NullPointerException e) {
            System.out.println("Response in null");
            e.printStackTrace();
        }

        return new MinMaxTemp(minTemp, maxTemp);

    }

    public static String getCityId(String cityInText) throws IOException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("q", cityInText)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String cityId = null;
        try {
            cityId = objectMapper.readTree(response.body().string())
                    .get(0)
                    .at("/Key")
                    .asText();
        } catch (NullPointerException e) {
            System.out.println("Response in null");
            e.printStackTrace();
        }

        return cityId;


    }

    public static String getData(String cityId) throws IOException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("5day")
                .addPathSegment(cityId)
                .addQueryParameter("apikey", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String json = response.body().string();

        String data = null;

        try {
            data = objectMapper.readTree(json)
                        .at("/DailyForecasts")
                        .get(0)
                        .at("/Date")
                        .asText();

        } catch (NullPointerException e) {
            System.out.println("Response in null");
            e.printStackTrace();
        }

        return data;

    }
}
