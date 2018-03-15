package hr.fer.android.hw1191226486.homework.networking;

import java.util.List;

import hr.fer.android.hw1191226486.homework.models.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tina on 6/27/17.
 */

public interface RetrofitService {
    @GET("{user}")
    Call<Repo> getRepo(@Path("user") String user);
}
