package ru.melonhell.cyberpunkserver.cp77;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private double x = 0;
    private double y = 0;
    private double z = 0;

    @Override
    protected Location clone() {
        return new Location(x, y, z);
    }
}
