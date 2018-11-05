package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
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
//            System.out.println(artist.toString());
            try{
                beginTx();
                entityManager.persist(song);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        }
    }
//dodaj Artist list, genre.
    public Song updateSong(int songId, Song song, Artist artist, Album album ) {
        if (getSong(songId) == null || song == null) {
            return null;
        }
        try{
            beginTx();
            song.setId(songId);
            song.setArtist(artist);
            song.setAlbum(album);
            entityManager.merge(song);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        return song;
    }

    public boolean removeSong(int songId) {
        Song song = entityManager.find(Song.class, songId);
        if (song != null) {
            try{
                beginTx();
                entityManager.remove(song);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
        }
            return true;
        }
        return false;
    }

    private void beginTx() {
        if (!entityManager.getTransaction().isActive())
            entityManager.getTransaction().begin();
    }

    private void commitTx() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }

    private void rollbackTx() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().rollback();
    }
}