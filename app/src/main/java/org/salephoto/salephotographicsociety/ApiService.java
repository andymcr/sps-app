package org.salephoto.salephotographicsociety;

import org.salephoto.models.Competition;
import org.salephoto.models.Event;
import org.salephoto.models.Talk;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;


public interface ApiService {
    @GET("/competitions")
    void listCompetitions(
        @Query("before") String before, @Query("after") String after, @Query("order") String order,
        @Query("limit") Integer limit, @Query("offset") Integer offset,
        Callback<List<Competition>> cb);

    @GET("/competitions/{cid}")
    void getCompetition(
        @Path("cid") int id,
        @Query("sectionOrder") String sectionOrder, @Query("entryOrder") String entryOrder,
        Callback<Competition> cb);

    @GET("/events")
    void listEvents(
        @Query("before") String before, @Query("after") String after, @Query("order") String order,
        @Query("limit") Integer limit, @Query("offset") Integer offset,
        Callback<List<Event>> cb);

    @GET("/talks")
    void listTalks(
        @Query("before") String before, @Query("after") String after, @Query("order") String order,
        @Query("limit") Integer limit, @Query("offset") Integer offset,
        Callback<List<Talk>> cb);
}
