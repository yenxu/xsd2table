class Element  {
    def name
    def typeName
    Type type
    def minOccurs, maxOccurs
    def path

    def to_string() {
        "E[name:"+name+" type:"+typeName+" minOccurs:"+minOccurs+" maxOccurs:"+maxOccurs+" path:"+path+"]"
    }
    def parsing(node, String parentPath) {
        if (node.name() == "element") {
            this.name = node.@name.text()
            this.path = parentPath+"/"+this.name
            this.typeName = node.@type.text()
            this.maxOccurs = node.@maxOccurs.text()
            this.minOccurs = node.@minOccurs.text()
        }
        println(to_string())
        this
    }

}