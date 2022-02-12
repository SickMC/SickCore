package me.anton.sickcore.core.module.globalmodule;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class GlobalModuleHandler {

    @Getter
    public List<GlobalModule> modules = Arrays.asList();

    public void load(){

    }

}
