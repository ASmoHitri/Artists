package entities;

import javax.persistence.*;

@Entity(name = "artists")
@NamedQueries(value =
        {
                @NamedQuery(name = "Artists.getAll", query = "SELECT a FROM artists a")
        })
public class Artist {

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
