package utilities.renderer

class PodTemplateRenderer implements Serializable {
    def render(String runtime) {
        def template = libraryResource("podtemplates/${runtime}.yaml")
        return template
    }
}
