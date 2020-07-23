package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.Auction;
import com.codesai.auction_house.business.model.AuctionRepository;

public class OverbidAuctionAction {
    private AuctionRepository repository;

    public OverbidAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public void execute(OverbidAuctionCommand command) {
        Auction auction = repository.retrieveById(command.auctionId);
        auction.overbid(command.bidAmount);
        repository.save(auction);
    }
}
