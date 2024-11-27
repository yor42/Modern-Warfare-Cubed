package com.paneedah.mwc.groovyscript;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;

import static com.paneedah.mwc.ProjectConstants.NAME;
import static com.paneedah.mwc.ProjectConstants.VERSION;

public class MWCLinkGenerator extends BasicLinkGenerator {

    @Override
    public String id() {
        return NAME;
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
