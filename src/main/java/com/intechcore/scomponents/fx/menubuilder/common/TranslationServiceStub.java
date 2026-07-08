package com.intechcore.scomponents.fx.menubuilder.common;

import com.intechcore.scomponents.common.core.i18n.II18nKey;
import com.intechcore.scomponents.common.core.i18n.II18nService;

public class TranslationServiceStub implements II18nService {
    @Override
    public String translate(II18nKey key) {
        return key == null ? null : key.getI18nKey();
    }

    @Override
    public String translate(II18nKey key, String lang) {
        return this.translate(key);
    }

    @Override
    public void addTranslationsResource(String s) {
    }

    @Override
    public boolean loadAllBundles() {
        return true;
    }

    @Override
    public void setLanguage(String s) {
    }

    @Override
    public String getCurrentLanguage() {
        return "";
    }
}
