package ru.melonhell.cyberpunkserver.cp77;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Player {
    private final int id;
    private final Location location;

    public Location getLocation() {
        return location.clone();
    }
}
