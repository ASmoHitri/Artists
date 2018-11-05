package entities;

import javax.persistence.*;

@Entity(name = "songs")
//@NamedQueries(value =
//        {
//                @NamedQuery(name = "Artists.getAll", query = "SELECT a FROM artists a")
//                @NamedQuery(name = "Artists.add", query = "INSERT INTO artists (name) VALUES (artist)")
//        })

public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;


    @Column(nullable = false)
    private List<Artist> artists;

    @Column()
    private Album album;

    @Columns()
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



    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
