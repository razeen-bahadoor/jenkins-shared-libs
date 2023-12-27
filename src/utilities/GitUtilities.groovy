package utilities

class GitUtilities {

    static String getBranchType(String branchName) {
        def matcher = (branchName =~ /^(release|hotfix|master).*$/)
        if (matcher.matches()) {
            return matcher[0][1]
        }
        return "development"
    }

    static String getShortCommit(steps) {
        return steps.sh(
            returnStdout: true, script: "git log -n 1 --pretty=format:'%h'"
        ).trim()  
    }

    static void gitClone(steps, String repoURL) {
        steps.sh "git clone ${repoURL}"
    }


    static String getCloneURL(String baseURL, String repoName) {
        return "${baseURL}/${repoName}.git"
    }

    static void stageCommit(steps,String message) {
        steps.sh "git commit -m ${message} --all"
    }

    static void gitPush(steps) {
        steps.sh "git push"
    }
}