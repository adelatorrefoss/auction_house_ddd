package auction_house.unit.bussines.actions;

import com.codesai.auction_house.business.actions.ConquerAuctionAction;
import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.CannotConquerAClosedAuctionException;
import com.codesai.auction_house.business.model.bidder.Bidder;
import com.codesai.auction_house.business.model.bidder.BidderId;
import com.codesai.auction_house.business.model.bidder.BidderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static auction_house.helpers.builder.AuctionBuilder.anAuction;
import static auction_house.helpers.matchers.AuctionConqueredMatcher.anAuctionConqueredBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class ConquerAuctionActionShould {

    private final AuctionRepository auctions = mock(AuctionRepository.class);
    private final BidderRepository bidders = mock(BidderRepository.class);
    private final ConquerAuctionAction conquerAuction = new ConquerAuctionAction(auctions, bidders);
    private final BidderId bidderId = new BidderId("anyUser");
    private final Bidder bidder = new Bidder(bidderId);

    @BeforeEach
    public void beforeEach() {
        when(bidders.retrieveById(bidderId)).thenReturn(bidder);
    }

    @Test public void
    win_the_auction() {
        var aLiveAuction = givenALiveAuction();

        conquerAuction.execute(new ConquerAuctionActionCommand(bidderId.id, aLiveAuction.id));

        verify(auctions).save(argThat(anAuctionConqueredBy(bidderId)));
    }
    
    @Test public void
    cannot_conquer_a_closed_auction() {
        var aClosedAuction = aClosedAuction();

        assertThatThrownBy(() -> conquerAuction.execute(new ConquerAuctionActionCommand(bidderId.id, aClosedAuction.id))).isInstanceOf(CannotConquerAClosedAuctionException.class);
    }

    private Auction aClosedAuction() {
        var auction = givenALiveAuction();
        conquerAuction.execute(new ConquerAuctionActionCommand(bidderId.id, auction.id));
        return auction;
    }

    private Auction givenALiveAuction() {
        var anAuction = anAuction().build();
        when(auctions.retrieveById(anAuction.id)).thenReturn(anAuction);
        return anAuction;
    }
}

