package entities;

import javax.persistence.*;

@Entity(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre_id")
    private Genre genre;


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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}

