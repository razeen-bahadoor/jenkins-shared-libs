package utilities

class PodTemplateRenderer implements Serializable {
    static String basePath = "podtemplates/"
    def render(String runtime) {
        def template = libraryResource("${basePath}${runtime}.yaml")
        return template
    }
}
