package org.salephoto.salephotographicsociety.repository;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.salephoto.models.Competition;
import org.salephoto.models.Event;
import org.salephoto.models.Talk;
import org.salephoto.salephotographicsociety.ApiService;
import org.salephoto.salephotographicsociety.bus.BusProvider;
import org.salephoto.salephotographicsociety.events.AbstractListEvent;
import org.salephoto.salephotographicsociety.events.CompetitionEvent;
import org.salephoto.salephotographicsociety.events.CompetitionsListedEvent;
import org.salephoto.salephotographicsociety.events.EventsListedEvent;
import org.salephoto.salephotographicsociety.events.GetCompetitionEvent;
import org.salephoto.salephotographicsociety.events.ListEventEvent;
import org.salephoto.salephotographicsociety.events.TalksListedEvent;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class RepositoryService {
    private ApiService api;
    private Bus bus;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public RepositoryService() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private Bus getBus() {
        if (bus == null) {
            bus = BusProvider.getBus();
        }

        return bus;
    }

    private ApiService getApi() {
        if (api == null) {
            final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerizlier())
                .create();
            final RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", MediaType.APPLICATION_JSON);
                }
            };
            final RestAdapter restAdapter = new RestAdapter.Builder()
//                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint("http://sps.andycarpenter.me.uk/api")
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();
            api = restAdapter.create(ApiService.class);
        }

        return api;
    }

    @Subscribe
    public void onListCompetitions(final ListEventEvent<Competition> event) {
        getApi().listCompetitions(formatDate(event.getBefore()), formatDate(event.getAfter()),
            dateOrder(event.getOrder()), event.getLimit(), event.getOffset(),
            new Callback<List<Competition>>() {
                @Override
                public void success(List<Competition> competitions, Response response) {
                    getBus().post(new CompetitionsListedEvent(event.getRequesterId(), competitions));
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getKind() == RetrofitError.Kind.NETWORK) {
                        Log.d(getClass().getSimpleName(), "List competitions failure", error);
                    }
                    getBus().post(new ApiErrorEvent(error));
                }
            });
    }

    @Subscribe
    public void onGetCompetition(final GetCompetitionEvent event) {
        getApi().getCompetition(event.getCompetitionId(),
                event.getSectionOrder().queryValue(), event.getEntryOrder().queryValue(),
                new Callback<Competition>() {
                    @Override
                    public void success(Competition competition, Response response) {
                        getBus().post(new CompetitionEvent(competition));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        getBus().post(new ApiErrorEvent(error));
                    }
                });
    }

    @Subscribe
    public void onListEvents(final ListEventEvent<Event> event) {
        getApi().listEvents(formatDate(event.getBefore()), formatDate(event.getAfter()),
                dateOrder(event.getOrder()), event.getLimit(), event.getOffset(),
                new Callback<List<Event>>() {
                    @Override
                    public void success(List<Event> events, Response response) {
                        getBus().post(new EventsListedEvent(event.getRequesterId(), events));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getKind() == RetrofitError.Kind.NETWORK) {
                            Log.d(getClass().getSimpleName(), "List events failure", error);
                        }
                        getBus().post(new ApiErrorEvent(error));
                    }
                });
    }

    @Subscribe
    public void onListTalks(final ListEventEvent<Talk> event) {
        getApi().listTalks(formatDate(event.getBefore()), formatDate(event.getAfter()),
                dateOrder(event.getOrder()), event.getLimit(), event.getOffset(),
                new Callback<List<Talk>>() {
                    @Override
                    public void success(List<Talk> talks, Response response) {
                        getBus().post(new TalksListedEvent(event.getRequesterId(), talks));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getKind() == RetrofitError.Kind.NETWORK) {
                            Log.d(getClass().getSimpleName(), "List talks failure", error);
                        }
                        getBus().post(new ApiErrorEvent(error));
                    }
                });
    }

    private String formatDate(final Date date) {
        String formattedDate = null;
        if (date != null) {
            formattedDate = dateFormat.format(date);
        }

        return formattedDate;
    }

    private String dateOrder(Map<String, AbstractListEvent.Order> order) {
        String orderText = null;
        if (order.containsKey(ListEventEvent.FIELD_DATE)) {
            orderText = ListEventEvent.FIELD_DATE + order.get(ListEventEvent.FIELD_DATE).name();
        }

        return orderText;
    }

    private static final String[] DATE_FORMATS = new String[] {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd"
    };

    private class DateSerizlier implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOf,
                                JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                try {
                    return new SimpleDateFormat(format).parse(jsonElement.getAsString());
                } catch (ParseException ignored) {
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        }
    }

}
