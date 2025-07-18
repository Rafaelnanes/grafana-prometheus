package com.user.model;

import lombok.Data;

@Data
public class LeakedObject {
    private final byte[] largeData;

    public LeakedObject() {
        this.largeData = new byte[1024 * 1024]; // 1 MB
    }

}
