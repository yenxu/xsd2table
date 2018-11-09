import spock.lang.Specification

class ComplexTypeTest extends Specification {
    def complexTypeOneSeq = """
<xsd:schema xmlns="urn:ske:fastsetting:innsamling:aksjesparekonto:v1" 
    xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
    targetNamespace="urn:ske:fastsetting:innsamling:aksjesparekonto:v1" 
    elementFormDefault="qualified" attributeFormDefault="unqualified">
<xsd:complexType name="Oppgavegiver">
    <xsd:sequence>
      <xsd:element name="organisasjonsnummer" type="Organisasjonsnummer" />
      <xsd:element name="organisasjonsnavn" type="Organisasjonsnavn" minOccurs="0" />
      <xsd:element name="internasjonalIdentifikator" type="InternasjonalIdentifikator" minOccurs="0" maxOccurs="unbounded" />
      <xsd:element name="kontaktinformasjon" type="Kontaktinformasjon" minOccurs="0" />
    </xsd:sequence>
  </xsd:complexType>
  
</xsd:schema>
"""
    def complexTypeChoice = """
<xsd:schema xmlns="urn:ske:fastsetting:innsamling:aksjesparekonto:v1" 
    xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
    targetNamespace="urn:ske:fastsetting:innsamling:aksjesparekonto:v1" 
    elementFormDefault="qualified" attributeFormDefault="unqualified">
  <xsd:complexType name="Disponent">
    <xsd:sequence>
      <xsd:choice>
        <xsd:sequence>
          <xsd:element name="organisasjonsnummer" type="Organisasjonsnummer" />
          <xsd:element name="organisasjonsnavn" type="Organisasjonsnavn" />
        </xsd:sequence>
        <xsd:sequence>
          <xsd:element name="foedselsnummer" type="Foedselsnummer" />
          <xsd:element name="bostedsland" type="Landkode" minOccurs="0" maxOccurs="unbounded" />
          <xsd:element name="fornavn" type="Navn" />
          <xsd:element name="mellomnavn" type="Navn" minOccurs="0" />
          <xsd:element name="etternavn" type="Navn" />
          <xsd:element name="foedselsdato" type="Foedselsdato" minOccurs="0" />
        </xsd:sequence>
      </xsd:choice>
      <xsd:element name="internasjonalIdentifikator" type="InternasjonalIdentifikator" minOccurs="0" maxOccurs="unbounded" />
      <xsd:element name="adresse" type="InternasjonalAdresse" minOccurs="0" maxOccurs="unbounded" />
      <xsd:element name="internasjonalRapportering" type="InternasjonalRapporteringKontohaver" minOccurs="0" maxOccurs="unbounded" />
    </xsd:sequence>
  </xsd:complexType>
   
</xsd:schema>

"""

    def "parsing complexTypeOneSeq"() {
        setup:
        def xsd = new XmlSlurper().parseText(complexTypeOneSeq).declareNamespace("xsd":"http://www.w3.org/2001/XMLSchema")
        def complexType = new ComplexType()

        when:
        complexType.parsing(xsd.complexType)

        then:
        complexType.name == "Oppgavegiver"
        complexType.sequences != null
        complexType.sequences[0].elements.size() == 4
        complexType.sequences[0].sequences.size() == 0
    }

    def "parsing complexTypeChoice Ok"() {
        setup:
        def xsd = new XmlSlurper().parseText(complexTypeChoice).declareNamespace("xsd":"http://www.w3.org/2001/XMLSchema")
        def complexType = new ComplexType()

        when:
        complexType.parsing(xsd.complexType)

        then:
        complexType.name == "Disponent"
        complexType.sequence.sequences.size() == 2
        complexType.sequence.elements.size() == 3
    }
}
