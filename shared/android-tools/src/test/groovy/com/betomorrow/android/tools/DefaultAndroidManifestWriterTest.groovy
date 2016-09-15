package com.betomorrow.android.tools

import com.betomorrow.android.tools.manifest.DefaultAndroidManifestWriter
import groovy.xml.Namespace
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class DefaultAndroidManifestWriterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    String SAMPLE_MANIFEST = ClassLoader.getSystemResource('AndroidManifest.xml').file;
    File copy;

    DefaultAndroidManifestWriter editor

    @Before
    public void setUp() {
        copy = testFolder.newFile("copy.manifest")
        Files.copy(Paths.get(SAMPLE_MANIFEST), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        editor = new DefaultAndroidManifestWriter(copy.absolutePath);
    }

    @Test
    public void testWriteUpdatesAttributes() {
        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")

        // Given
        editor.versionName = "1.1.0"
        editor.versionCode = "14"
        editor.packageName = "another.package.name"

        // When
        editor.write();

        // Then
        def content = new XmlParser().parse(copy);

        assert content.attribute(ns.versionName) == "1.1.0"
        assert content.attribute(ns.versionCode) == "14"
        assert content.attribute("package") == "another.package.name"
    }



}