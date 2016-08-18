package com.betomorrow.android.tools

import groovy.xml.Namespace
import groovy.xml.XmlUtil

class AndroidManifestEditor {

    private String source;

    String versionCode
    String versionName
    String packageName

    AndroidManifestEditor(String source) {
        this.source = source
    }

    void write() {

        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")

        XmlParser parser = new XmlParser()
        def content = parser.parse(source);

        if (!isNullOrEmpty(packageName)) {
            content.@package = packageName;
        }

        if (!isNullOrEmpty(versionName)) {
            content.attributes()[ns.versionName] = versionName;
        }

        if (!isNullOrEmpty(versionCode)) {
            content.attributes()[ns.versionCode] = versionCode;
        }

        new FileOutputStream(source).withStream { out ->
            XmlUtil.serialize(content, out)
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }


}