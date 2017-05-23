package auction.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Entity
public class Bid {

    @Embedded
    private FontysTime time;
    @ManyToOne(cascade=CascadeType.PERSIST)
    private User buyer;
    @Embedded
    private Money amount;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
        this.amount = amount;
        time = FontysTime.now();
    }
    
    public Bid(){
        
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
    
    public long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
}
