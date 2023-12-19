import utilities.*
import static tmp.*

def call(String env) {
    String template = "nodejs"
    render()
    def renderer= new podTemplateRenderer()
    String renderedTemplate = renderer.render(template)
    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: renderedTemplate) {
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