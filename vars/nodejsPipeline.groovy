import utilities.*

def call(String env) {
    String template = "nodejs"
    def renderer= new podTemplateRenderer()
    String renderedTemplate = renderer.render(template)
    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: renderedTemplate) {
     node(POD_LABEL) {
        try {
            container('ubuntu'){
                stage('preBuildCheck') {
                    preBuildChecks()
                }
            }

        } catch (ex) {
            throw ex
        }
     }
    }
}