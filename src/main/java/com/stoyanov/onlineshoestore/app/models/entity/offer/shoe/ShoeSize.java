package com.stoyanov.onlineshoestore.app.models.entity.offer.shoe;

public enum ShoeSize {
    THIRTY_FIVE(35),
    THIRTY_SIX(36),
    THIRTY_SEVEN(37),
    THIRTY_EIGHT(38),
    THIRTY_NINE(39),
    FOURTY(40),
    FOURTY_ONE(41),
    FOURTY_TWO(42),
    FOURTY_THREE(43),
    FOURTY_FOUR(44),
    FOURTY_FIVE(45),
    FOURTY_SIX(46),
    FOURTY_SEVEN(47),
    FOURTY_EIGHT(48),
    FOURTY_NINE(49),
    FIFTY(50);

    private int size;

    ShoeSize(int size) {
        this.size = size;
    }
}
