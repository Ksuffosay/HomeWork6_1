import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class HomeWork6_project {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        String cityName = sc.nextLine();

        String cityId = RequestSender.getCityId(cityName);
        System.out.println(cityId);
        System.out.println(RequestSender.getData(cityId));
        System.out.println(RequestSender.getTempMinAndMaxTemp(cityId));


    }


}
