package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.files.FileUtils
import org.junit.Test

class SolutionLoaderTest {

    def SAMPLE_SOLUTION = FileUtils.getResourcePath('CrossApp/CrossApp.sln')

    @Test
     void testLoadRealSolution() {
        def descriptor = new SolutionLoader().load(SAMPLE_SOLUTION)

        assert descriptor.getProjects().size() == 3
        assert descriptor.getProject('CrossApp') != null

        def iosApp = descriptor.getProject('CrossApp.iOS')
        assert  iosApp != null
        assert !iosApp.isAndroidApplication()
        assert iosApp.isIosApplication()
        assert iosApp.getAssemblyName() == 'CrossApp.iOS'
        assert iosApp.getOutputPath('Release', 'iPhone') == SAMPLE_SOLUTION.parent.resolve('iOS/bin/iPhone/Release/CrossApp.iOS.ipa')
        assert iosApp.getOutputPath('Debug', 'iPhone') == SAMPLE_SOLUTION.parent.resolve('iOS/bin/iPhone/Debug/CrossApp.iOS.ipa')

        def androidApp = descriptor.getProject('CrossApp.Droid')
        assert androidApp != null
        assert androidApp.isAndroidApplication()
        assert !androidApp.isIosApplication()
        assert androidApp.getAssemblyName() == 'CrossApp.Droid'
        assert androidApp.getOutputPath('Release') == SAMPLE_SOLUTION.parent.resolve('Droid/bin/Release/com.acme.crossapp.alpha.apk')
        assert androidApp.getOutputPath('Debug') == SAMPLE_SOLUTION.parent.resolve('Droid/bin/Debug/com.acme.crossapp.alpha.apk')

    }
}
