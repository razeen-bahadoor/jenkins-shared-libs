package utilities

class RenderEngine {
    def String render(String runtime) {
            def template = libraryResource("podtemplates/${runtime}.yaml")
            return template
    }
}