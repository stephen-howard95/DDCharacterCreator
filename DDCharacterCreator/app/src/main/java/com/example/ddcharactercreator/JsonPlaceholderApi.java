package com.example.ddcharactercreator;

import retrofit2.http.Query;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    @GET("spells")
    Call<SpellContainer> getSpellContainer(@Query("page") int pageNumber);
}
