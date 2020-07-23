package auction_house.unit.actions;

import com.codesai.auction_house.business.actions.OverbidAuctionAction;
import com.codesai.auction_house.business.actions.OverbidAuctionCommand;
import com.codesai.auction_house.business.model.Auction;
import com.codesai.auction_house.business.model.AuctionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.UUID;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class OverbidAuctionActionShould {
    public static final String ANY_ITEM_NAME = "An Item" + UUID.randomUUID();
    public static final double ANY_BID_AMOUNT = 15.0;
    public static final String ANY_DESCRIPTION = "AnyDescription" + UUID.randomUUID();
    public static final double ANY_INITIAL_BID_AMOUNT = 10.0;
    public static final double ANY_CONQUER_PRICE_AMOUNT = 100.0;
    public static final LocalDate ANY_EXPIRATION_DAY = now();
    public static final String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID();

    @Test
    public void overbid_auction() {

        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        Auction auction = Auction.anAuction(ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                money(ANY_INITIAL_BID_AMOUNT),
                money(ANY_CONQUER_PRICE_AMOUNT),
                ANY_EXPIRATION_DAY,
                ANY_OWNER_ID);
        OverbidAuctionCommand command = new OverbidAuctionCommand(auction.id, ANY_BID_AMOUNT);

        when(auctionRepository.retrieveById(auction.id)).thenReturn(auction);

        OverbidAuctionAction action = new OverbidAuctionAction(auctionRepository);
        action.execute(command);

        ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);
        verify(auctionRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getCurrentBidAmount()).isEqualTo(money(ANY_BID_AMOUNT));
    }
}
