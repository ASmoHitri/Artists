package entities;

import javax.persistence.*;

@Entity(name = "genres")
@NamedQueries(value =
        {
                @NamedQuery(name = "Genres.getGenreByName", query = "SELECT g FROM genres g WHERE g.name=:name")
        })
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

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
}
