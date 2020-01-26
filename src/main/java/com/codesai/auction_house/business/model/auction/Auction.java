package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.auction.exceptions.*;
import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;

public class Auction {
    private static final Money MINIMUM_MONEY_TO_OVERBID = money(1);

    public String id;
    public Item item;
    public Money startingPrice;
    public Money conquerPrice;
    public LocalDate expirationDate;
    public Money minimumOverbiddingPrice;
    public List<Bid> bids;

    public Auction(Item item, Money startingPrice, Money conquerPrice, LocalDate expirationDate, Money minimumOverbiddingPrice) {
        if (conquerPrice.isLessThan(startingPrice)) throw new InitialBidIsGreaterThanConquerPrice();
        if (minimumOverbiddingPrice.isLessThan(MINIMUM_MONEY_TO_OVERBID))
            throw new MinimumOverbiddingPriceIsNotAllowed();
        if (expirationDate.isAfter(now().plusWeeks(2))) throw new ExpirationDayIsTooFar();
        if (expirationDate.isBefore(now())) throw new ExpirationDayAlreadyPassed();
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.startingPrice = startingPrice;
        this.conquerPrice = conquerPrice;
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = minimumOverbiddingPrice;
        this.bids = new LinkedList<>();
    }

    public void bid(Bid bid) {
        topBid().ifPresentOrElse(
                currentBid -> {
                    if (currentBid.money.isGreaterThan(bid.money)) throw new TopBidIsGreater();
                    if (bid.money.equals(currentBid.money)) throw new BidAmountCannotBeTheSameAsTheCurrentOne();
                    this.bids.add(0, bid);
                }, () -> {
                    if (bid.money.isLessThan(startingPrice)) throw new FirstBidShouldBeGreaterThanStartingPrice();
                    this.bids.add(0, bid);
                });
    }

    private Optional<Bid> topBid() {
        if (bids.size() > 0)  return Optional.of(bids.get(0));
        return Optional.empty();
    }

    public Bid currentBid() {
        return topBid().get();
    }

    public void conquerBy(String userId) {
        expirationDate = LocalDate.now().minusDays(1);
        bids.add(0, new Bid(conquerPrice, userId));
    }

    public Optional<Bid> winner() {
        if (bids.isEmpty()) return Optional.empty();
        return topBid();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
