package auction_house.unit.actions;

import com.codesai.auction_house.business.actions.OverbidAuctionAction;
import com.codesai.auction_house.business.actions.OverbidAuctionCommand;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class OverbidAuctionActionShould {
    public static final String ANY_ITEM_NAME = "An Item" + UUID.randomUUID();
    public static final double ANY_BID_AMOUNT = 15.0;

    @Test
    public void overbid_auction() {
        OverbidAuctionCommand command = new OverbidAuctionCommand(ANY_ITEM_NAME, ANY_BID_AMOUNT);

        OverbidAuctionAction action = new OverbidAuctionAction();
        action.execute(command);


    }
}
