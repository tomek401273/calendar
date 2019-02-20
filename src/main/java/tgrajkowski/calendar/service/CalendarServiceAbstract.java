package tgrajkowski.calendar.service;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import tgrajkowski.calendar.model.EventDto;
import tgrajkowski.calendar.model.EventExeption;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


public abstract class CalendarServiceAbstract {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/credentials";
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        File file = new File(CREDENTIALS_FILE_PATH);
        InputStream fileInputStream = new FileInputStream(file);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(fileInputStream));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    static Calendar getCalendar() throws EventExeption{
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            return new Calendar.Builder(HTTP_TRANSPORT, CalendarServiceAbstract.JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            throw new EventExeption(e.getMessage());
        }
    }

    public abstract List<EventDto> getAllEvents() throws IOException, GeneralSecurityException, EventExeption;
    public abstract EventDto addEvent(EventDto eventDto) throws EventExeption, IOException;
    public abstract boolean deleteEvent(String id) throws EventExeption;
    public abstract EventDto updateEvent(EventDto eventDto) throws EventExeption;
}
