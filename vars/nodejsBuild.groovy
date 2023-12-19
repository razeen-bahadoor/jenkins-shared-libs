def call(String env) {
    def podTemplate = libraryResource 'org/podtemplates/nodejs.yaml'
    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: podTemplate) {
     node(POD_LABEL) {
        try {
            stage('test') {
                container('ubuntu') {
                    echo "test ${env}"
                }
            }
        } catch (ex) {
            throw ex
        }
     }
    }
}