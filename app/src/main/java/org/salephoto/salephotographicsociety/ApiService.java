package org.salephoto.salephotographicsociety;

import org.salephoto.models.Competition;
import org.salephoto.models.Event;
import org.salephoto.models.Section;
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
        @Query("limit") Integer limit, @Query("offset") Integer offset, @Query("fields") String fields,
        Callback<List<Competition>> cb);

    @GET("/competitions/{cid}")
    void getCompetition(
        @Path("cid") int id, @Query("sectionOrder") String sectionOrder,
        @Query("entryOrder") String entryOrder, @Query("fields") String fields,
        Callback<Competition> cb);

    @GET("/competitions/{cid}/sections")
    void getSections(
            @Path("cid") int id,  @Query("sectionOrder") String sectionOrder,
            @Query("entryOrder") String entryOrder, @Query("fields") String fields,
            Callback<List<Section>> cb);

    @GET("/events")
    void listEvents(
        @Query("before") String before, @Query("after") String after, @Query("order") String order,
        @Query("limit") Integer limit, @Query("offset") Integer offset,
        @Query("fields") String fields,
        Callback<List<Event>> cb);

    @GET("/events/{eid}")
    void getEvent(
            @Path("eid") int id, @Query("fields") String fields,
            Callback<Event> cb);

    @GET("/talks")
    void listTalks(
        @Query("before") String before, @Query("after") String after, @Query("order") String order,
        @Query("limit") Integer limit, @Query("offset") Integer offset,
        @Query("fields") String fields,
        Callback<List<Talk>> cb);
}
