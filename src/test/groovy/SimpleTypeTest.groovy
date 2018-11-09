import spock.lang.Specification

class SimpleTypeTest extends Specification {
    def xsdString = """
<xsd:schema xmlns="urn:ske:fastsetting:innsamling:aksjesparekonto:v1" 
    xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
    targetNamespace="urn:ske:fastsetting:innsamling:aksjesparekonto:v1" 
    elementFormDefault="qualified" attributeFormDefault="unqualified">
  <xsd:simpleType name="Telefonnummer">
    <xsd:restriction base="xsd:string">
      <xsd:maxLength value="20" />
      <xsd:minInclusive value="0" />
      <xsd:maxInclusive value="999999999999" />
      <xsd:minExclusive value="0" />
      <xsd:maxInclusive value="999999999999" />
      <xsd:totalDigits value="4" />
    </xsd:restriction>
  </xsd:simpleType>

</xsd:schema>
"""

    def "parsing XSD ok"() {
        setup:
        def xsd = new XmlSlurper().parseText(xsdString).declareNamespace("xsd":"http://www.w3.org/2001/XMLSchema")
        def simpleType = new SimpleType()

        when:
        simpleType.parsing(xsd.simpleType)

        then:
        simpleType.name == "Telefonnummer"
        simpleType.baseType == "xsd:string"
        simpleType.maxLength == "20"
        simpleType.minInclusive == "0"
        simpleType.maxInclusive == "999999999999"
        simpleType.minExclusive == "0"
        simpleType.totalDigits == "4"
    }
}
