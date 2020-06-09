package com.example.ddcharactercreator;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    @GET("spells")
    Call<List<Spell>> getSpells();
}
