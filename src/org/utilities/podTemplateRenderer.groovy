package org.utilities

class PodTemplateRenderer implements Serializable {
    def render(String runtime) {
        def template = libraryResource("podtemplates/${runtime}.yaml")
        return template
    }
}
