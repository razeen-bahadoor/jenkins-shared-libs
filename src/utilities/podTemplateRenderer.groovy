package utilities

def call(String runtime) {
        def template = libraryResource("podtemplates/${runtime}.yaml")
        return template
}
return this

