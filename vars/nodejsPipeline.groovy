
def call(String env) {
    String template = "nodejs"
    String renderedTemplate = podTemplateRenderer(template)
    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: renderedTemplaten) {
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