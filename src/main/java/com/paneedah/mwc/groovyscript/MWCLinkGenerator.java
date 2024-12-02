package com.paneedah.mwc.groovyscript;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;

import static com.paneedah.mwc.ProjectConstants.*;

public class MWCLinkGenerator extends BasicLinkGenerator {

    @Override
    public String id() {
        return ID;
    }

    @Override
    protected String version() {
        return VERSION;
    }

    @Override
    protected String defaultBranch() {
        return "next";
    }

    @Override
    protected String domain() {
        return "https://github.com/Cubed-Development/Modern-Warfare-Cubed/";
    }
}
