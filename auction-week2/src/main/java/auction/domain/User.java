package auction.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.eclipse.persistence.annotations.CascadeOnDelete;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "select a from User as a"),
    @NamedQuery(name = "User.count", query = "select count(a) from User as a"),
    @NamedQuery(name = "User.findByEmail", query = "select a from User as a where a.email = :email")
})
public class User {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    @CascadeOnDelete
    private Set<Bid> bids;
    @CascadeOnDelete
//    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
//    private Set<Item> items;
    @OneToMany(mappedBy= "seller", cascade = CascadeType.REMOVE)
    private Set<Item> offeredItems = new HashSet();

    public User()
    {
        
    }
    
    public Iterator<Item> getOfferedItems()
    {
        return this.offeredItems.iterator();
    }
    
    public User(String email) {
        this.email = email;

    }
    
    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
    
    private void addItem(Item item)
    {
        offeredItems.add(item);
    }
    
    public int numberOfOfferdItems()
    {
        return offeredItems.size();
    }

}
