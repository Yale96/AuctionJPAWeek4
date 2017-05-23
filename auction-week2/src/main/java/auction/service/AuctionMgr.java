package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuctionMgr {

    private final EntityManagerFactory emf;
    private EntityManager em;
    private ItemDAO itemDAO;

    public AuctionMgr() {
        emf = Persistence.createEntityManagerFactory("nl.fhict.se42_auction_jar_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(em);
    }

    /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     * geretourneerd
     */
    public Item getItem(Long id) {
        return itemDAO.find(id);
    }

    /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        return itemDAO.findByDescription(description);
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     * amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
    if (item.getHighestBid() == null) {
            item.newBid(buyer, amount);
            return item.getHighestBid();
        }
        else if (item.getHighestBid().getAmount().getCents() < amount.getCents()) {
            item.newBid(buyer, amount);
            return item.getHighestBid();
        }
        return null;
    }  
}
