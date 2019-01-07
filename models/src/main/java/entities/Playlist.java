package entities;

import javax.persistence.*;
import java.util.List;

@Entity(name="playlists")
@NamedQueries(value =
        {
                @NamedQuery(name = "Playlists.getByName&User", query = "SELECT p FROM playlists p WHERE p.name=:name AND p.userId=:userId"),
                @NamedQuery(name = "Playlists.updateName", query = "UPDATE playlists p SET p.name=:name")
        })
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToMany
    @JoinTable(name = "playlists_songs",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"))
    private List<Song> songs;


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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
