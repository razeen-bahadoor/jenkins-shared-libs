import utilities.renderer.*

def call(String env) {
    String template = "nodejs"
    PodTemplateRenderer renderer = new PodTemplateRenderer()
    String renderedTemplate = renderer.render(template)
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