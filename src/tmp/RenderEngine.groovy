package tmp

class RenderEngine {
    static def String render(String runtime) {
            def template = libraryResource("podtemplates/${runtime}.yaml")
            return template
    }
}