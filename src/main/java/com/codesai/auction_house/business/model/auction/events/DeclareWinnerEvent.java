package com.codesai.auction_house.business.model.auction.events;

import com.codesai.auction_house.business.model.bidder.BidderId;
import com.codesai.auction_house.business.model.generic.Event;
import com.codesai.auction_house.business.model.generic.Money;

public class DeclareWinnerEvent extends Event {
    // TODO: deberian los eventos usar siempre tipos básicos? (como los comand, estan en el boundary del modelo y sirvén para comunicar sucesos al exterior)
    public final BidderId winner;
    public final String auctionId;
    public final Money bidWinnerAmount;

    public DeclareWinnerEvent(BidderId winner, String auctionId, Money bidWinnerAmount) {
        this.winner = winner;
        this.auctionId = auctionId;
        this.bidWinnerAmount = bidWinnerAmount;
    }
}
