package org.salephoto.salephotographicsociety.repository;

import android.content.Context;
import android.os.StrictMode;

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
import org.salephoto.models.Section;
import org.salephoto.models.Talk;
import org.salephoto.salephotographicsociety.ApiService;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.bus.BusProvider;
import org.salephoto.salephotographicsociety.events.AbstractListEvent;
import org.salephoto.salephotographicsociety.events.AbstractListEventEvent;
import org.salephoto.salephotographicsociety.events.CompetitionEvent;
import org.salephoto.salephotographicsociety.events.CompetitionUpdatedEvent;
import org.salephoto.salephotographicsociety.events.CompetitionsListedEvent;
import org.salephoto.salephotographicsociety.events.EventEvent;
import org.salephoto.salephotographicsociety.events.EventsListedEvent;
import org.salephoto.salephotographicsociety.events.GetCompetitionEvent;
import org.salephoto.salephotographicsociety.events.GetEventEvent;
import org.salephoto.salephotographicsociety.events.ListCompetitionsEvent;
import org.salephoto.salephotographicsociety.events.ListEventsEvent;
import org.salephoto.salephotographicsociety.events.ListTalksEvent;
import org.salephoto.salephotographicsociety.events.TalksListedEvent;
import org.salephoto.salephotographicsociety.events.UpdateCompetitionEvent;

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

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        //= new SimpleDateFormat(context.getResources().getString(R.string.full_time_pattern));


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
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint("http://sps.andycarpenter.me.uk/api")
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();
            api = restAdapter.create(ApiService.class);
        }

        return api;
    }

    @Subscribe
    public void onListCompetitions(final ListCompetitionsEvent event) {
        getApi().listCompetitions(formatDate(event.getBefore()), formatDate(event.getAfter()),
            dateOrder(event.getOrder()), event.getLimit(), event.getOffset(), event.getFieldQuery(),
            new Callback<List<Competition>>() {
                @Override
                public void success(List<Competition> competitions, Response response) {
                    getBus().post(new CompetitionsListedEvent(event.getRequesterId(), competitions));
                }

                @Override
                public void failure(RetrofitError error) {
                    getBus().post(new ApiErrorEvent(error));
                }
            });
    }

    @Subscribe
    public void onGetCompetition(final GetCompetitionEvent event) {
        getApi().getCompetition(event.getCompetitionId(), event.getSectionOrder().queryValue(),
                event.getEntryOrder().queryValue(), event.getFieldQuery(),
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
    public void onUpdateCompetition(final UpdateCompetitionEvent event) {
        final Competition competition =event.getCompetition();
        getApi().getSections(competition.getId(),
                null, null, event.getFieldQuery(),
                new Callback<List<Section>>() {
                    @Override
                    public void success(List<Section> sections, Response response) {
                        competition.setSections(sections);
                        getBus().post(new CompetitionUpdatedEvent(competition));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        getBus().post(new ApiErrorEvent(error));
                    }
                });
    }

    @Subscribe
    public void onListEvents(final ListEventsEvent event) {
        getApi().listEvents(formatDate(event.getBefore()), formatDate(event.getAfter()),
                dateOrder(event.getOrder()), event.getLimit(), event.getOffset(),
                event.getFieldQuery(),
                new Callback<List<Event>>() {
                    @Override
                    public void success(List<Event> events, Response response) {
                        getBus().post(new EventsListedEvent(event.getRequesterId(), events));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        getBus().post(new ApiErrorEvent(error));
                    }
                });
    }

    @Subscribe
    public void onGetEvent(final GetEventEvent event) {
        getApi().getEvent(event.getEventId(), event.getFieldQuery(),
                new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        getBus().post(new EventEvent(event));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        getBus().post(new ApiErrorEvent(error));
                    }
                });
    }

    @Subscribe
    public void onListTalks(final ListTalksEvent event) {
        getApi().listTalks(formatDate(event.getBefore()), formatDate(event.getAfter()),
                dateOrder(event.getOrder()), event.getLimit(), event.getOffset(),
                event.getFieldQuery(),
                new Callback<List<Talk>>() {
                    @Override
                    public void success(List<Talk> talks, Response response) {
                        getBus().post(new TalksListedEvent(event.getRequesterId(), talks));
                    }

                    @Override
                    public void failure(RetrofitError error) {
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
        if (order.containsKey(AbstractListEventEvent.FIELD_DATE)) {
            orderText = AbstractListEventEvent.FIELD_DATE + order.get(AbstractListEventEvent.FIELD_DATE).name();
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
