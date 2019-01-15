package beans;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dtos.Lyrics;
import entities.Song;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;

@ApplicationScoped
public class LyricsBean {

    public Lyrics getSongLyrics(Song song) {
        try {
            String songTitle = song.getTitle().toLowerCase().replace(" ","%20");
            String songArtist = song.getArtist().getName().toLowerCase().replace(" ", "%20");
            String queryString = String.format("q_track=%s&q_artist=%s", songTitle, songArtist);
            HttpResponse<JsonNode> response = Unirest.get("https://api.musixmatch.com/ws/1.1/matcher.lyrics.get?"+ queryString + "&apikey=bd12a85973bd5fc86edb0908b650edd0")
                    .asJson();
            Lyrics lyrics = new Lyrics();
            JSONObject jsonObject = (JSONObject) response.getBody().getObject().get("message");
            JSONObject headerObject = (JSONObject) jsonObject.get("header");
            if(headerObject.get("status_code").toString().equals("200")) {
                lyrics.setSongId(song.getId());
                String lyricsString = ((JSONObject)((JSONObject) jsonObject.get("body")).get("lyrics")).getString("lyrics_body");
                lyricsString = lyricsString.replace("******* This Lyrics is NOT for Commercial use *******","");
                lyrics.setLyrics(lyricsString);
                return lyrics;
            }
        } catch (WebApplicationException | ProcessingException | UnirestException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
        return null;
    }
}
