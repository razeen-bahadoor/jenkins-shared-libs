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
}