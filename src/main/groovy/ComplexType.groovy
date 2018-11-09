import groovy.util.slurpersupport.GPathResult

class ComplexType extends Type {
    String path
    def choiceList = [][]
    def elements = []
    def to_string() {
         "ComplexType name:"+name+ " path:"+path
    }
    def parsing(GPathResult xmlNode, parentPaht) {
        if (xmlNode.name() == "complexType") {
            this.name = xmlNode.@name.text()
            path = parentPaht+"/"+this.name
            def el = []
            xmlNode.sequence.choice.sequence.each  {
                print("Choice=>")
                it.element.each {
                 el << new Element().parsing(it, path)
                }
                choiceList << el
                assert choiceList.size() > 0
            }
            xmlNode.sequence.element?.each {
                elements << new Element().parsing(it, path)
            }
            println "Parsing:"+ to_string()
        }
        return this
    }
}