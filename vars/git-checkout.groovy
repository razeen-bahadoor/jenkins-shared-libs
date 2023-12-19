def gitCheckout(String url, String revision) {
    def workingDir = "${env.WORKSPACE}"
    sh "git clone ${repoUrl} ${workingDir}"
    sh "git checkout ${revision}"
    return workingDir
}