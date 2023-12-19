def call(String url, String revision) {
    def workingDir = "${env.WORKSPACE}"
    sh "git clone ${url} ${workingDir}"
    sh "git checkout ${revision}"
    return workingDir
}

