package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Album;
import entities.Artist;
import entities.Song;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class SongBean {

    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "catalogs-jpa")
    private EntityManager entityManager;

    public List<Song> getSongs() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        return JPAUtils.queryEntities(entityManager, Song.class, queryParameters);
    }

    public Song getSong(int songId) {
        return entityManager.find(Song.class, songId);
    }

    public void addSong(Song song) {
        if (song != null) {
            try{
                TransactionsHandler.beginTx(entityManager);
                entityManager.persist(song);
                TransactionsHandler.commitTx(entityManager);
            } catch (Exception e) {
                TransactionsHandler.rollbackTx(entityManager);
            }
        }
    }
//dodaj Artist list, genre.
    public Song updateSong(int songId, Song song, List<Artist> artists, Album album ) {
        if (getSong(songId) == null || song == null) {
            return null;
        }
        try{
            TransactionsHandler.beginTx(entityManager);
            song.setId(songId);
            //song.setArtist(artist);
            song.setAlbum(album);
            entityManager.merge(song);
            TransactionsHandler.commitTx(entityManager);
        } catch (Exception e) {
            TransactionsHandler.rollbackTx(entityManager);
        }
        return song;
    }

    public boolean removeSong(int songId) {
        Song song = entityManager.find(Song.class, songId);
        return TransactionsHandler.removeObject(entityManager, song);
    }
}