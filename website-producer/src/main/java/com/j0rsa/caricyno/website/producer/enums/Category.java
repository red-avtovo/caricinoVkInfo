package com.j0rsa.caricyno.website.producer.enums;

/**
 * @author red
 * @since 07.05.17
 */

public enum Category {
    PRESS_RELEASE(4),
    ARTICLE(19),
    OTHER(20);

    private int categoryId;
    Category(int id) {
        categoryId = id;
    }
}
