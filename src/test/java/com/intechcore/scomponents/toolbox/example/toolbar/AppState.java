/*
 *  Copyright (C) 2008-2025 Intechcore GmbH - All Rights Reserved
 *
 *  This file is part of SComponents project
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *
 *  Proprietary and confidential
 *
 *  Written by Intechcore GmbH <info@intechcore.com>
 */

package com.intechcore.scomponents.toolbox.example.toolbar;

public class AppState {
    private boolean toolOneEnabled;
    private boolean toolSecondEnabled;
    private boolean toolThirdEnabled;

    public AppState() {
    }

    public boolean isToolOneEnabled() {
        return this.toolOneEnabled;
    }

    public void setToolFirstEnabled(boolean toolOneEnabled) {
        this.toolOneEnabled = toolOneEnabled;
    }

    public boolean isToolSecondEnabled() {
        return this.toolSecondEnabled;
    }

    public void setToolSecondEnabled(boolean toolSecondEnabled) {
        this.toolSecondEnabled = toolSecondEnabled;
    }

    public boolean isToolThirdEnabled() {
        return this.toolThirdEnabled;
    }

    public void setToolThirdEnabled(boolean toolThirdEnabled) {
        this.toolThirdEnabled = toolThirdEnabled;
    }

    @Override
    public String toString() {
        return "AppState {" +
                " toolOneEnabled=" + this.toolOneEnabled +
                ", toolSecondEnabled=" + this.toolSecondEnabled +
                ", toolThirdEnabled=" + this.toolThirdEnabled +
                " }";
    }
}
