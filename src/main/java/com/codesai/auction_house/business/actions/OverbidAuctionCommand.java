package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.generic.Money;

import static com.codesai.auction_house.business.model.generic.Money.money;

public class OverbidAuctionCommand {
    public final String auctionId;
    public final Money bidAmount;

    public OverbidAuctionCommand(String auctionId, double bidAmount) {
        this.auctionId = auctionId;
        this.bidAmount = money(bidAmount);
    }
}
