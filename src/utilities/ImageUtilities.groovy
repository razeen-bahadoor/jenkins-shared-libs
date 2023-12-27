package utilities
import java.lang.StringBuilder

class ImageUtilities {

    static Boolean isValidReleaseImageTag(String imageTag){
        return (imageTag ==~ /^[\w\d\.]+\-\d+\-[a-z0-9]{7}\-(release|hotfix|master)$/)
    }

    static String generateImageTag(String version, String buildNumber, String shortCommit, string branchType) {
        return String.join("-",version,buildNumber,shortCommit,branchType)
    }

}