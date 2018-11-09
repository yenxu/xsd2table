import groovy.util.slurpersupport.GPathResult

def xsdFileName = './../resources/aksjesparekonto_v1_0.xsd'

def xsdFile = new File(xsdFileName).text

  xsdDoc = new XmlSlurper().parseText(xsdFile)

  def targetNamespace =xsdDoc.@targetNamespace

  assert targetNamespace == "urn:ske:fastsetting:innsamling:aksjesparekonto:v1"

  println "targetNamespace:"+targetNamespace

  def simpleTypeList = []

  xsdDoc.simpleType.each {
        simpleTypeList << new SimpleType().parsing(it)
  }
  assert simpleTypeList.size() > 0

  def complexTypeList = []
  xsdDoc.complexType.each {
    assert it  instanceof GPathResult
      complexTypeList << new ComplexType().parsing(it, null)
  }


try {
    complexTypeList.each {
        println("ComplexType.elements.size="+it.elements.size())
        it.elements.each { el ->
                println("InitTypeComplexType->el.type="+el.typeName)
                el.type = complexTypeList.find { it.name == el.typeName}
                if (el.type == null) {
                    println("InitTypeSimpleType->"+el.typeName)
                    el.type = simpleTypeList.find { it.name  == el.typeName }
                }
            }
        it.choiceList.each{
            it.each { el->
                println("InitTypeComplexType->el.type="+el.typeName)
                el.type = complexTypeList.find { it.name == el.typeName}
                if (el.type == null) {
                    println("InitTypeSimpleType->"+el.typeName)
                    el.type = simpleTypeList.find { it.name  == el.typeName }
                }
            }
        }
        //println it.to_string()
//        println "file name:"+it.name
//        def tableFile = new File("./../../../out/"+it.name+".csv")
//        tableFile << it.to_string()

    }
} catch(e) {
    print('Scripting error '+ e)
}
