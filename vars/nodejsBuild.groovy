import org.utilities

def call(String env, String runtime) {
    PodTemplateRenderer renderer = new PodTemplateRenderer(runtime)
    def template = renderer.render()
    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: template) {
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