package test
class RenderEngine {
    static String render(String runtime) {
        def template = libraryResource("podtemplates/${runtime}.yaml")
        return template
    }
}