import groovy.transform.CompileStatic

//https://stackoverflow.com/questions/11866901/is-there-any-global-flag-for-groovy-static-compilation
withConfig(configuration) {
    ast(CompileStatic)
}
