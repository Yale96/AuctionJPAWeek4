package auction.domain;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import nl.fontys.util.Money;

@Entity
@NamedQueries({
    @NamedQuery(name = "Item.getAll", query = "select i from Item as i"),
    @NamedQuery(name = "Item.count", query = "select count(i) from Item as i"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description"),
    @NamedQuery(name = "Item.findById", query = "select i from Item as i where i.id = :id") 
})
public class Item implements Comparable {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne(cascade=CascadeType.PERSIST)
    private User seller;
   @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "description", column = @Column(name = "category_description"))
    })

    private Category category;
    private String description;
    @OneToOne(cascade=CascadeType.PERSIST)
    private Bid highest;
    
    
    
    
    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }
    
    public Item(){
        
    }
    
    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        EntityManager em =Persistence.createEntityManagerFactory("nl.fhict.se42_auction_jar_1.0-SNAPSHOTPU").createEntityManager();
        em.getTransaction().begin();
        em.merge(highest);
        em.merge(this);
        em.getTransaction().commit();
        return highest;

    }

    public int compareTo(Object arg0) {
       return id.compareTo(((Item)arg0).id);
    }

    public boolean equals(Object o) {
         if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Item other = (Item) o;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.seller, other.seller)) {
            return false;
        }
        if (this.category != other.category) {
            return false;
        }
        if (this.description.equals(other.description)) {
            return false;
        }
        if (this.highest != other.highest) {
            return false;
        }
        return true;

    }

    public int hashCode() {
        //TODO
        return id.intValue();
    }
}
