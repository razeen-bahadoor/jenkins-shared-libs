def call(String env) {
    def renderer = new org.utilities.podTemplateRenderer()
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