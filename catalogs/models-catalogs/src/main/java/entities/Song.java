package entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

//    @OneToMany(mappedBy="song")
//    //@Column(nullable = false) - kako to spraviti v @OneToMany??
//    private List<Artist> artists;

//    @ManyToOne
//    @JoinColumn(name = "album_id")
//    private Album album;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public List<Artist> getArtists() {
//        return artists;
//    }
//
//    public void setArtists(List<Artist> artists) {
//        this.artists = artists;
//    }

//    public Album getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(Album album) {
//        this.album = album;
//    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
