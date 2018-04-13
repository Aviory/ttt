package com.llc111minutes.gameday.api;

import com.llc111minutes.gameday.model.HeadlinesRetrofit;
import com.llc111minutes.gameday.model.MenuItemTemplate;
import com.llc111minutes.gameday.model.Overlays;
import com.llc111minutes.gameday.model.Stadias;
import com.llc111minutes.gameday.model.TeamInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    @GET("/api/template_groups")
    Observable<List<MenuItemTemplate>> getAllTemplatesGroups();

    @GET("/api/teams")
    Observable<List<TeamInfo>> getAllTeams();

    @GET("/api/teams")
    Call<List<TeamInfo>> getPageTeams(@Query("page") int numPage);

    @GET("/api/teams")
    Call<List<TeamInfo>> getSearchTeams(@Query("q[name_contains]") String searchString);

    @GET("/api/teams")
    Call<List<TeamInfo>> getSearchTeams(@Query("q[name_contains]") String searchString, @Query("page") int numPage);

    @GET("/api/teams")
    Call<List<TeamInfo>> getCountTeams(@Query("per_page") int countPerPage);

    @GET("/api/overlays")
    Observable<Overlays> getAllOverlays();

    @GET("/api/stadia")
    Observable<List<Stadias>> getAllStadias();

    @GET("/api/stadia")
    Call<List<Stadias>> getPageStadias(@Query("page") int numPage);

    @GET("/api/stadia")
    Call<List<Stadias>> getSearchStadias(@Query("q[name_contains]") String searchString);

    @GET("/api/stadia")
    Call<List<Stadias>> getSearchStadias(@Query("q[name_contains]") String searchString, @Query("page") int numPage);

    @GET("/api/stadia")
    Call<List<Stadias>> getCountStadias(@Query("per_page") int countPerPage);


    @GET("/api/headlines")
    Observable<HeadlinesRetrofit> getHeadlines();
}