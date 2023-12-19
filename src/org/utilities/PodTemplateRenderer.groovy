package org.utilities

def render(String runtime) {
        def template = libraryResource("podtemplates/${runtime}.yaml")
        return template
}
return this

