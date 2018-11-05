package entities;

import javax.persistence.*;

@Entity(name = "albums")
//@NamedQueries(value =
//        {
//                @NamedQuery(name = "Artists.getAll", query = "SELECT a FROM artists a")
//                @NamedQuery(name = "Artists.add", query = "INSERT INTO artists (name) VALUES (artist)")
//        })

public class Album { \\manjkajo getterji in setterji ker je vse sivo?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;


    @Column(nullabe = false) //tako?
    private List<Artist> artists;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}

