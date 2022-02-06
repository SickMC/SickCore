package me.anton.sickcore.api.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Replacable {

    public CharSequence key;
    public CharSequence replacement;

}
